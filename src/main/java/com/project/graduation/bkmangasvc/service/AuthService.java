package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.UpdateAuthPasswordRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateInfoProfileRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetAuthInfoResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UpdateAuthPasswordResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UpdateInfoProfileResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UserLoginResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface AuthService {
    ApiResponse<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO) throws CustomException;

    ApiResponse<?> register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException;

    ApiResponse<GetAuthInfoResponseDTO> getAuthInfo() throws CustomException;

    ApiResponse<UpdateInfoProfileResponseDTO> updateInfoProfile(
            UpdateInfoProfileRequestDTO updateInfoProfileRequestDTO
    ) throws CustomException;

    ApiResponse<UpdateAuthPasswordResponseDTO> updateAuthPassword(
            UpdateAuthPasswordRequestDTO updateAuthPasswordRequestDTO
    ) throws CustomException;
}
