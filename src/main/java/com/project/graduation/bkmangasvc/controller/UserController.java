package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetUserManagementResponseDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/user")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<GetUserManagementResponseDTO>> getUserList(
            @Valid @RequestBody GetUserListRequestDTO getUserListRequestDTO
    ) {
        return userService.getUserList(getUserListRequestDTO);
    }

    @PostMapping("/detail")
    public ApiResponse<GetUserManagementResponseDTO> getUserDetail(
        @Valid @RequestBody GetUserDetailRequestDTO getUserDetailRequestDTO
    ) throws CustomException {
        return userService.getUserDetail(getUserDetailRequestDTO);
    }

    @PutMapping(path = "/update/status")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> updateStatusUser(
            @Valid @RequestBody UpdateStatusUserRequestDTO updateStatusUserRequestDTO
    ) throws CustomException{
        return userService.updateStatusUser(updateStatusUserRequestDTO);
    }

    @PutMapping(path = "/update/info")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> updateInfoUser(
        @Valid @RequestBody UpdateInfoUserRequestDTO updateInfoUserRequestDTO
    ) throws CustomException {
        return userService.updateInfoUser(updateInfoUserRequestDTO);
    }

    @PutMapping(path = "/update/password")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> updatePasswordUser(
            @Valid @RequestBody UpdatePasswordUserRequestDTO updatePasswordUserRequestDTO
    ) throws CustomException {
        return userService.updatePasswordUser(updatePasswordUserRequestDTO);
    }
}
