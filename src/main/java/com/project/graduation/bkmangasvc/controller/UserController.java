package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.ChangeStatusUserRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetUserListRequestDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<User>> getUserList(
            @RequestBody GetUserListRequestDTO getUserListRequestDTO
    ) {
        return userService.getUserList(getUserListRequestDTO);
    }

    @PutMapping(path = "/changeStatus")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<User> changeStatusUser(
            @RequestBody ChangeStatusUserRequestDTO changeStatusUserRequestDTO
    ) throws CustomException{
        return userService.changeStatusUser(changeStatusUserRequestDTO);
    }
}
