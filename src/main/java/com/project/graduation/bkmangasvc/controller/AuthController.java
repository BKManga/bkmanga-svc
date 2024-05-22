package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
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
    public ApiResponse<UserLoginResponseDTO> login(@Valid @RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        return authService.login(userLoginRequestDTO);
    }

    @PostMapping(path = "/register")
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<?> register(
            @Valid @RequestBody UserRegisterRequestDTO userRegisterRequestDTO
    ) throws CustomException {
        return authService.register(userRegisterRequestDTO);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(path = "/test/user")
    public String testUser() {
        return "USER";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/test/admin")
    public String testAdmin() {
        return "ADMIN";
    }

    @GetMapping(path = "/testing")
    public ApiResponse<String> test() {
        return ApiResponse.successWithResult("TEST");
    }
}
