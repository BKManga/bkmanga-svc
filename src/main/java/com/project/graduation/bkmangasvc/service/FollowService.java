package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.CreateFollowResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetFollowResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface FollowService {
    ApiResponse<GetFollowResponseDTO> getFollow(
            GetFollowRequestDTO getFollowRequestDTO
    ) throws CustomException;

    ApiResponse<CreateFollowResponseDTO> createFollow(
            CreateFollowRequestDTO createFollowRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteFollow(
            DeleteFollowRequestDTO deleteFollowRequestDTO
    ) throws CustomException;
}
