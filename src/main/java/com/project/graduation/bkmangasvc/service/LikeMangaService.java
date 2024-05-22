package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.GetLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.CreateLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface LikeMangaService {
    ApiResponse<GetLikeMangaResponseDTO> getLikeManga(
            GetLikeMangaRequestDTO getLikeMangaRequestDTO
    ) throws CustomException;

    ApiResponse<CreateLikeMangaResponseDTO> createLikeManga(
            CreateLikeMangaRequestDTO createLikeMangaRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteLikeManga(
            DeleteLikeMangaRequestDTO deleteLikeMangaRequestDTO
    ) throws CustomException;
}
