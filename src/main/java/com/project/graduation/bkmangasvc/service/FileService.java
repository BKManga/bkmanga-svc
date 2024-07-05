package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    ApiResponse<?> uploadLogoManga(MultipartFile file, Long mangaId, String target) throws CustomException;

    ApiResponse<?> uploadImageProfile(MultipartFile file, String username) throws CustomException;

    ApiResponse<?> uploadImageUserProfile(MultipartFile file, Long userId) throws CustomException;

    ResponseEntity<byte[]> getImageLogoManga(Long mangaId) throws CustomException;

    ResponseEntity<byte[]> getImageLargeManga(Long mangaId) throws CustomException;

    ResponseEntity<byte[]> getImageProfileUser(Long userId) throws CustomException;

    ResponseEntity<byte[]> getImageProfile(String username) throws CustomException;

    ApiResponse<List<String>> getAllImageChapter(Long mangaId, Long chapterId) throws CustomException;

    ResponseEntity<byte[]> getImageChapter(Long mangaId, Long chapterId, String imageName) throws CustomException;

    void copyProfileImage(Long userId) throws CustomException;

    ApiResponse<?> uploadChapterManga(MultipartFile file, Long chapterId, Long mangaId) throws CustomException;
}
