package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.CreateFollowResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetFollowByMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface FollowService {
    ApiResponse<Page<GetMangaResponseDTO>> getListFollowByUser(
            GetListFollowRequestDTO getListFollowRequestDTO
    ) throws CustomException;

    ApiResponse<GetFollowByMangaResponseDTO> getFollowByManga(
            GetFollowByMangaRequestDTO getFollowByMangaRequestDTO
    ) throws CustomException;

    ApiResponse<CreateFollowResponseDTO> createFollow(
            CreateFollowRequestDTO createFollowRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteFollow(
            DeleteFollowRequestDTO deleteFollowRequestDTO
    ) throws CustomException;
}
