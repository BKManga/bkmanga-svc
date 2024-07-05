package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetUserManagementResponseDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    User findByUsername(String username) throws CustomException;

    ApiResponse<Page<GetUserManagementResponseDTO>> getUserList(
            GetUserListRequestDTO getUserListRequestDTO)
            ;

    ApiResponse<?> updateStatusUser(
            UpdateStatusUserRequestDTO updateStatusUserRequestDTO
    ) throws CustomException;

    ApiResponse<GetUserManagementResponseDTO> getUserDetail(
            GetUserDetailRequestDTO getUserDetailRequestDTO
    ) throws CustomException;

    ApiResponse<?> updateInfoUser(UpdateInfoUserRequestDTO updateInfoUserRequestDTO) throws CustomException;

    ApiResponse<?> updatePasswordUser(UpdatePasswordUserRequestDTO updatePasswordUserRequestDTO) throws CustomException;
}
