package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.UserLoginResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface AuthService {
    ApiResponse<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO);
    ApiResponse<?> register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException;
}
