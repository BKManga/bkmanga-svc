package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.GetAllImageUrlChapterRequestDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.FileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/file")
@CrossOrigin(origins = "*")
public class FileController {
    private final FileService fileService;

    @PostMapping(path = "/manga/image/upload", consumes = { "multipart/form-data" })
    public ApiResponse<?> uploadImageManga(
            @RequestParam("file") MultipartFile file,
            @RequestParam("mangaId") Long mangaId,
            @RequestParam("target") String target
    ) throws CustomException {
        return fileService.uploadLogoManga(file, mangaId, target);
    }

    @PostMapping(path = "user/profile/upload", consumes = { "multipart/form-data" })
    public ApiResponse<?> uploadProfile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) throws CustomException {
        return fileService.uploadImageProfile(file, username);
    }

    @PostMapping(path = "user/manage/profile/upload", consumes = { "multipart/form-data" })
    public ApiResponse<?> uploadProfileUser(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Long userId
    ) throws CustomException {
        return fileService.uploadImageUserProfile(file, userId);
    }

    @GetMapping(path = "/manga/image_logo/{mangaId}")
    public ResponseEntity<byte[]> getImageLogoManga(
            @PathVariable Long mangaId
    ) throws CustomException {
        return fileService.getImageLogoManga(mangaId);
    }

    @GetMapping(path = "/manga/image_large/{mangaId}")
    public ResponseEntity<byte[]> getImageLargeManga(
            @PathVariable Long mangaId
    ) throws CustomException {
        return fileService.getImageLargeManga(mangaId);
    }

    @GetMapping(path = "/image/manga/{mangaId}/chapter/{chapterId}/{imageName:.+}")
    public ResponseEntity<byte[]> getImageChapter(
            @PathVariable Long mangaId,
            @PathVariable Long chapterId,
            @PathVariable String imageName
    ) throws CustomException {
        return fileService.getImageChapter(mangaId, chapterId, imageName);
    }

    @GetMapping(path = "/user/profile/manage/{userId}")
    public ResponseEntity<byte[]> getImageProfileUser(
            @PathVariable Long userId
    ) throws CustomException {
        return fileService.getImageProfileUser(userId);
    }

    @GetMapping(path = "/user/profile/{username}")
    public ResponseEntity<byte[]> getImageProfile(
            @PathVariable String username
    ) throws CustomException {
        return fileService.getImageProfile(username);
    }

    @PostMapping(path = "/chapter/image/all")
    public ApiResponse<List<String>> getAllImageUrlChapter(
            @Valid @RequestBody GetAllImageUrlChapterRequestDTO getAllImageUrlChapterRequestDTO
    ) throws CustomException {
        return fileService.getAllImageChapter(
                getAllImageUrlChapterRequestDTO.getMangaId(),
                getAllImageUrlChapterRequestDTO.getChapterId()
        );
    }

    @PostMapping(path = "/chapter/upload", consumes = { "multipart/form-data" })
    public ApiResponse<?> uploadChapterManga(
            @RequestParam("file") MultipartFile file,
            @RequestParam("chapterId") Long chapterId,
            @RequestParam("mangaId") Long mangaId
    ) throws CustomException {
        return fileService.uploadChapterManga(file, chapterId, mangaId);
    }
}
