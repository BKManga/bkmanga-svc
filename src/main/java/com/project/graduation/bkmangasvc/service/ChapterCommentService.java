package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateChapterCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.ChapterComment;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface ChapterCommentService {
    ApiResponse<Page<ChapterComment>> getChapterCommentByMangaId(
            GetListChapterCommentRequestDTO getListChapterCommentRequestDTO
    ) throws CustomException;

    ApiResponse<CreateChapterCommentResponseDTO> createChapterComment(
            CreateChapterCommentRequestDTO createChapterCommentRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteChapterComment(
            DeleteChapterCommentRequestDTO deleteChapterCommentRequestDTO
    ) throws CustomException;
}
