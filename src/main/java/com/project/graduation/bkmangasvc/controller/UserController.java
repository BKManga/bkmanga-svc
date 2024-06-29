package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.UpdateStatusUserRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetUserListRequestDTO;
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
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(path = "/get")
    public ApiResponse<Page<User>> getUserList(
            @Valid @RequestBody GetUserListRequestDTO getUserListRequestDTO
    ) {
        return userService.getUserList(getUserListRequestDTO);
    }

    @PostMapping("/detail")
    public ApiResponse<User> getUserDetail(

    ) throws CustomException {
        return null;
    }

    @PutMapping(path = "/update/status")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<User> updateStatusUser(
            @Valid @RequestBody UpdateStatusUserRequestDTO updateStatusUserRequestDTO
    ) throws CustomException{
        return userService.updateStatusUser(updateStatusUserRequestDTO);
    }

    @PutMapping(path = "/update/info")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<User> updateInfoUser(

    ) throws CustomException {
        return null;
    }
}
