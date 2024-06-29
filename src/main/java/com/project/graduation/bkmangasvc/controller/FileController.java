package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.GetAllImageUrlChapterRequestDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
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

    @PostMapping(path = "/manga/image/upload")
    public ApiResponse<?> uploadImageManga(
            @RequestParam("file") MultipartFile file,
            @RequestParam("mangaId") Long mangaId,
            @RequestParam("target") String target
    ) throws CustomException {
        return fileService.uploadLogoManga(file, mangaId, target);
    }

    @GetMapping(path = "/manga/image-logo/{mangaId}")
    public ResponseEntity<byte[]> getImageLogoManga(
            @PathVariable Long mangaId
    ) throws CustomException {
        return fileService.getImageLogoManga(mangaId);
    }

    @GetMapping(path = "/manga/image-large/{mangaId}")
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

    @GetMapping(path = "/user/profile/{userId}")
    public ResponseEntity<byte[]> getImageProfileManga(
            @PathVariable Long userId
    ) throws CustomException {
        return fileService.getImageProfileManga(userId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(path = "/user/profile")
    public ResponseEntity<byte[]> getImageProfileUserManga() throws CustomException {
        return fileService.getImageProfileUserManga();
    }

    @PostMapping("/image/chapter/all")
    public ApiResponse<List<String>> getAllImageUrlChapter(
            @Valid @RequestBody GetAllImageUrlChapterRequestDTO getAllImageUrlChapterRequestDTO
    ) throws CustomException {
        return fileService.getAllImageChapter(
                getAllImageUrlChapterRequestDTO.getMangaId(),
                getAllImageUrlChapterRequestDTO.getChapterId()
        );
    }
}
