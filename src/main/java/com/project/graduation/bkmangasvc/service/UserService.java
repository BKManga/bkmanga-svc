package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.ChangeStatusUserRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetUserListRequestDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    User findByUsername(String username) throws CustomException;

    ApiResponse<Page<User>> getUserList(GetUserListRequestDTO getUserListRequestDTO);

    ApiResponse<User> changeStatusUser(ChangeStatusUserRequestDTO changeStatusUserRequestDTO) throws CustomException;
}
