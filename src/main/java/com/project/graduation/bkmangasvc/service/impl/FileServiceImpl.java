package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.controller.FileController;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.FileService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final String mangaStorageGeneralDir = "uploads/manga/";
    private final String userStorageGeneralDir = "uploads/user/";
    private final String chapterStorageGeneralDir = "/chapter/";
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<?> uploadLogoManga(MultipartFile file, Long mangaId, String target) throws CustomException {
        final Path storagePath = Paths.get(mangaStorageGeneralDir + mangaId);

        createDirectory(storagePath);
        boolean checkFile = (isCorrectExtension(file) && isCorrectImageSize(file));

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String imageLogoName = target + "." + extension;

        Path destination = storagePath.resolve(Paths.get(imageLogoName)).normalize().toAbsolutePath();

        storageImage(file, destination);

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ResponseEntity<byte[]> getImageLogoManga(Long mangaId) throws CustomException {
        try {
            Path file = Paths.get(mangaStorageGeneralDir + mangaId).resolve("image_logo.png");

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(bytes);
            }

            throw new CustomException(ErrorCode.IMAGE_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImageLargeManga(Long mangaId) throws CustomException {
        try {
            Path file = Paths.get(mangaStorageGeneralDir + mangaId).resolve("image_large.png");

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(bytes);
            }

            throw new CustomException(ErrorCode.IMAGE_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImageProfileManga(Long userId) throws CustomException {
        try {
            Path file = Paths.get(userStorageGeneralDir + userId).resolve("profile.png");

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(bytes);
            }

            throw new CustomException(ErrorCode.IMAGE_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImageProfileUserManga() throws CustomException {
        return getImageProfileManga(TokenHelper.getPrincipal());
    }

    @Override
    public ApiResponse<List<String>> getAllImageChapter(Long mangaId, Long chapterId) throws CustomException {
        try {
            Path storagePath = Paths.get(mangaStorageGeneralDir + mangaId + chapterStorageGeneralDir + chapterId);
            Stream<Path> pathStream = Files.walk(storagePath, 1)
                    .filter(path -> !path.equals(storagePath))
                    .map(storagePath::relativize);

            List<String> urlImageList = pathStream.map(path -> {
                String url = MvcUriComponentsBuilder.fromMethodName(
                        FileController.class,
                        "getImageChapter",
                         mangaId,
                         chapterId,
                         path.getFileName().toString()
                ).build().toUri().toString();
                return url;
            }).toList();

            return ApiResponse.successWithResult(urlImageList);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_ERROR);
        }
    }

    @Override
    public ResponseEntity<byte[]> getImageChapter(
            Long mangaId,
            Long chapterId,
            String imageName
    ) throws CustomException {
        try {
            Path file = Paths.get(mangaStorageGeneralDir + mangaId + chapterStorageGeneralDir + chapterId).resolve(imageName);

            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(bytes);
            }

            throw new CustomException(ErrorCode.IMAGE_ERROR);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_ERROR);
        }
    }

    private boolean isCorrectExtension(MultipartFile multipartFile) throws CustomException{
        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        if (!Arrays.asList(new String[] {"png"}).contains(fileExtension.trim().toLowerCase()))
        {
            throw new CustomException(ErrorCode.IMAGE_EXTENSION);
        }

        return true;
    }

    private boolean isCorrectImageSize(MultipartFile multipartFile) throws CustomException{
        float imageSize = multipartFile.getSize() / 1_000_000f;

        if (imageSize > 5.0f) {
            throw new CustomException(ErrorCode.IMAGE_SIZE);
        }

        return true;
    }

    private void storageImage(
            MultipartFile multipartFile,
            Path destinationFilePage
    ) throws CustomException {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            Files.copy(inputStream, destinationFilePage, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    private void createDirectory(Path storagePath) throws CustomException {
        try {
            Files.createDirectories(storagePath);

        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    private User getUserValue(Long userId) throws CustomException{

        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }
}
