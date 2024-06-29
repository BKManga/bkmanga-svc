package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    ApiResponse<?> uploadLogoManga(MultipartFile file, Long mangaId, String target) throws CustomException;

    ResponseEntity<byte[]> getImageLogoManga(Long mangaId) throws CustomException;

    ResponseEntity<byte[]> getImageLargeManga(Long mangaId) throws CustomException;

    ResponseEntity<byte[]> getImageProfileManga(Long userId) throws CustomException;

    ResponseEntity<byte[]> getImageProfileUserManga() throws CustomException;

    ApiResponse<List<String>> getAllImageChapter(Long mangaId, Long chapterId) throws CustomException;

    ResponseEntity<byte[]> getImageChapter(Long mangaId, Long chapterId, String imageName) throws CustomException;
}
