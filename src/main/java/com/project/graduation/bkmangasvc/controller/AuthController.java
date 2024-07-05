package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.UpdateAuthPasswordRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateInfoProfileRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetAuthInfoResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UpdateAuthPasswordResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UserLoginResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.AuthService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    final private AuthService authService;

    @PostMapping(path = "/login")
    public ApiResponse<UserLoginResponseDTO> login(
            @Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO
    ) throws CustomException {
        return authService.login(userLoginRequestDTO);
    }

    @PostMapping(path = "/register")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<?> register(
            @Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO
    ) throws CustomException {
        return authService.register(userRegisterRequestDTO);
    }

    @GetMapping(path = "/info")
    public ApiResponse<GetAuthInfoResponseDTO> getAuthInfo() throws CustomException {
        return authService.getAuthInfo();
    }

    @PutMapping(path = "/info/update")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<?> updateAuthInfo(
            @Valid @RequestBody UpdateInfoProfileRequestDTO updateInfoProfileRequestDTO
    ) throws CustomException {
        return authService.updateInfoProfile(updateInfoProfileRequestDTO);
    }

    @PutMapping(path = "/password/update")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<UpdateAuthPasswordResponseDTO> updateAuthPassword(
            @Valid @RequestBody UpdateAuthPasswordRequestDTO updateAuthPasswordRequestDTO
    ) throws CustomException {
        return authService.updateAuthPassword(updateAuthPasswordRequestDTO);
    }
}
