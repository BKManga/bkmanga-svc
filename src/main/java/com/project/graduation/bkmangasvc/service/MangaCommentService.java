package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetMangaCommentDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateMangaCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.MangaComment;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface MangaCommentService {
    ApiResponse<Page<MangaComment>> getMangaCommentByMangaId(
            GetListMangaCommentRequestDTO mangaCommentListRequestDTO
    ) throws CustomException;

    ApiResponse<MangaComment> getMangaCommentDetail(
            GetMangaCommentDetailRequestDTO getMangaCommentDetailRequestDTO
    ) throws CustomException;

    ApiResponse<CreateMangaCommentResponseDTO> createMangaComment(
            CreateMangaCommentRequestDTO mangaCommentCreateRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteMangaComment(DeleteMangaCommentRequestDTO deleteMangaCommentRequestDTO) throws CustomException;
}
