package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.controller.FileController;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final String mangaStorageGeneralDir = "uploads/manga/";
    private final String userStorageGeneralDir = "uploads/user/";
    private final String chapterStorageGeneralDir = "/chapter/";
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final String tempStorageGeneralDir = "uploads/temp";

    @Override
    public ApiResponse<?> uploadLogoManga(MultipartFile file, Long mangaId, String target) throws CustomException {
        try {
            final Path storagePath = Paths.get(mangaStorageGeneralDir + mangaId);

            createDirectory(storagePath);
//            boolean checkFile = (isCorrectExtension(file) && isCorrectImageSize(file));

//        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String extension = "png";
            String imageLogoName = target + "." + extension;

            Path destination = storagePath.resolve(Paths.get(imageLogoName)).normalize().toAbsolutePath();

            byte[] bytes = file.getBytes();

            Files.write(destination, bytes);

//        storageImage(file, destination);

            return ApiResponse.successWithResult(null);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
    }

    @Override
    public ApiResponse<?> uploadImageProfile(MultipartFile file, String username) throws CustomException {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return uploadImageUserProfile(file, foundUser.get().getId());
    }

    @Override
    public ApiResponse<?> uploadImageUserProfile(MultipartFile file, Long userId) throws CustomException {
        try {
            final Path storagePath = Paths.get(userStorageGeneralDir + userId);

            createDirectory(storagePath);
//        boolean checkFile = (isCorrectExtension(file) && isCorrectImageSize(file));

            String imageProfileName = "profile.png";

            Path destination = storagePath.resolve(Paths.get(imageProfileName)).normalize().toAbsolutePath();

            byte[] bytes = file.getBytes();

            Files.write(destination, bytes);

//            storageImage(file, destination);

            return ApiResponse.successWithResult(null);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.IMAGE_UPLOAD_ERROR);
        }
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
    public ResponseEntity<byte[]> getImageProfileUser(Long userId) throws CustomException {
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
    public ResponseEntity<byte[]> getImageProfile(String username) throws CustomException {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return getImageProfileUser(foundUser.get().getId());
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

    public void copyProfileImage(Long userId) throws CustomException {
        try {
            final Path profilePath = Paths.get(userStorageGeneralDir + "profile.png");
            final Path storagePath = Paths.get(userStorageGeneralDir + userId);

            createDirectory(storagePath);

            Path destinationFilePage = storagePath.resolve("profile.png").normalize().toAbsolutePath();

            Files.copy(profilePath, destinationFilePage, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    @Override
    public ApiResponse<?> uploadChapterManga(MultipartFile file, Long chapterId, Long mangaId) throws CustomException {
        try {
            final Path storagePath = Paths.get(tempStorageGeneralDir + chapterStorageGeneralDir);

            createDirectory(storagePath);

            byte[] bytes = file.getBytes();
            final Path destinationTempPath = storagePath.resolve(chapterId + ".zip").normalize().toAbsolutePath();
            Files.write(destinationTempPath, bytes);

            unzipFile(destinationTempPath, chapterId, mangaId);

            Files.delete(destinationTempPath);

            return ApiResponse.successWithResult(null);
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CHAPTER_IMAGE_UPLOAD_ERROR);
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

    private void unzipFile(Path destinationZipFile, Long chapterId, Long mangaId) throws CustomException {
        try {
            Path destinationFile = Paths.get(mangaStorageGeneralDir + mangaId + chapterStorageGeneralDir + chapterId);
            createDirectory(destinationFile);
            ZipInputStream zis = new ZipInputStream(Files.newInputStream(destinationZipFile));
            ZipEntry zipEntry;
            int indexImage = 1;
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (checkEntry(zipEntry)) {
                    String filePath = destinationFile.resolve(indexImage + "." + MediaType.IMAGE_PNG.getSubtype())
                            .normalize()
                            .toAbsolutePath()
                            .toString();

                    extractFile(zis, filePath);
                    indexImage++;
                }
                zis.closeEntry();
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    private void extractFile(ZipInputStream zis, String filePath) throws CustomException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    private boolean checkEntry (ZipEntry zipEntry) {
        return (!zipEntry.isDirectory() &&
                zipEntry.getName().contains(MediaType.IMAGE_PNG.getSubtype()) &&
                !zipEntry.getName().contains("__MACOSX/")
        );
    }


}
