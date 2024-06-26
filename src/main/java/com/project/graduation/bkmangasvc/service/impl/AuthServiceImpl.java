package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetAuthInfoResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UserLoginResponseDTO;
import com.project.graduation.bkmangasvc.entity.Gender;
import com.project.graduation.bkmangasvc.entity.Level;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.GenderRepository;
import com.project.graduation.bkmangasvc.repository.LevelRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.AuthService;
import com.project.graduation.bkmangasvc.util.TokenUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final UserStatusRepository userStatusRepository;
    private final GenderRepository genderRepository;
    private final ModelMapper modelMapper;
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
    public ApiResponse<?> register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException {
        if (userRepository.existsUserByEmail(userRegisterRequestDTO.getEmail())) {
            throw new CustomException(ErrorCode.USER_EXISTED);
        }

        User user = new User();

        modelMapper.map(userRegisterRequestDTO, user);

        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));

        Optional<Gender> foundGender = genderRepository.findById(userRegisterRequestDTO.getGenderId());

        Optional<UserStatus> foundUserStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (foundGender.isEmpty() || foundUserStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        user.setUserStatus(foundUserStatus.get());
        user.setGender(foundGender.get());

        userRepository.save(user);

        Level level = new Level(user, 0L);

        levelRepository.save(level);
        return ApiResponse.successWithResult(true);
    }

    @Override
    public ApiResponse<GetAuthInfoResponseDTO> getAuthInfo() throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());
        return ApiResponse.successWithResult(modelMapper.map(user, GetAuthInfoResponseDTO.class));
    }

    private User getUserValue(Long userId) throws CustomException{

        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }
}
