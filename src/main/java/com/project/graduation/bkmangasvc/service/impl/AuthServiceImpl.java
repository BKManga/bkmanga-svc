package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserRole;
import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.UserLoginResponseDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.service.AuthService;
import com.project.graduation.bkmangasvc.util.TokenUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    private final TokenUtil tokenUtil;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ApiResponse<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestDTO.getLoginID(),
                        userLoginRequestDTO.getPassword()
                )
        );
        String token = tokenUtil.generateToken(authentication);
        return ApiResponse.successWithResult(new UserLoginResponseDTO(token));
    }

    @Override
    @Transactional(rollbackOn = {CustomException.class})
    public ApiResponse<?> register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException {
        if (userRepository.existsUserByEmail(userRegisterRequestDTO.getEmail())) {
            throw new CustomException(ErrorCode.USER_EXISTED);
        }

        User user = new User();

        user.setUsername(userRegisterRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setLevel(2L);
        user.setRole(UserRole.USER.getCode());
        user.setPhoneNumber(userRegisterRequestDTO.getPhoneNumber());
        user.setDateOfBirth(userRegisterRequestDTO.getDateOfBirth());
        user.setEmailVerify(false);
        user.setEmail(userRegisterRequestDTO.getEmail());
        userRepository.save(user);

        return ApiResponse.successWithResult(user);
    }
}
