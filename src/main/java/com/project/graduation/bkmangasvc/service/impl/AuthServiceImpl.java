package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.UpdateAuthPasswordRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateInfoProfileRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserLoginRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UserRegisterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetAuthInfoResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UpdateAuthPasswordResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.UpdateInfoProfileResponseDTO;
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
import com.project.graduation.bkmangasvc.service.FileService;
import com.project.graduation.bkmangasvc.util.TokenUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.catalina.Realm;
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
    private final FileService fileService;

    @Override
    public ApiResponse<UserLoginResponseDTO> login(UserLoginRequestDTO userLoginRequestDTO) throws CustomException {

        try {
            User userValid = getUserValid(userLoginRequestDTO.getLoginID());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDTO.getLoginID(),
                            userLoginRequestDTO.getPassword()
                    )
            );

            String token = tokenUtil.generateToken(authentication);
            return ApiResponse.successWithResult(new UserLoginResponseDTO(token));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }
    }

    @Override
    public ApiResponse<?> register(UserRegisterRequestDTO userRegisterRequestDTO) throws CustomException {

        if (userRepository.existsUserByEmail(userRegisterRequestDTO.getEmail())) {
            throw new CustomException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.findByUsername(userRegisterRequestDTO.getUsername()).isPresent()) {
            throw new CustomException(ErrorCode.USERNAME_EXISTED);
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

        fileService.copyProfileImage(user.getId());
        return ApiResponse.successWithResult(true);
    }

    @Override
    public ApiResponse<GetAuthInfoResponseDTO> getAuthInfo() throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());
        return ApiResponse.successWithResult(modelMapper.map(user, GetAuthInfoResponseDTO.class));
    }

    @Override
    public ApiResponse<UpdateInfoProfileResponseDTO> updateInfoProfile(
            UpdateInfoProfileRequestDTO updateInfoProfileRequestDTO
    ) throws CustomException {

        Long currentId = TokenHelper.getPrincipal();

        Gender gender = getGenderValue(updateInfoProfileRequestDTO.getGenderId());

        User user = getUserValue(currentId);
        user.setFullName(updateInfoProfileRequestDTO.getFullName());
        user.setGender(gender);

        userRepository.save(user);

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<UpdateAuthPasswordResponseDTO> updateAuthPassword(
            UpdateAuthPasswordRequestDTO updateAuthPasswordRequestDTO
    ) throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());

        boolean checkPassword = passwordEncoder.matches(
                updateAuthPasswordRequestDTO.getOldPassword(),
                user.getPassword()
        );

        if (!checkPassword) {
            throw new CustomException(ErrorCode.OLD_PASSWORD_NOT_CORRECT);
        }

        user.setPassword(passwordEncoder.encode(updateAuthPasswordRequestDTO.getNewPassword()));

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        updateAuthPasswordRequestDTO.getNewPassword()
                )
        );

        String token = tokenUtil.generateToken(authentication);
        return ApiResponse.successWithResult(new UpdateAuthPasswordResponseDTO(token));
    }

    private Gender getGenderValue(Integer genderId) throws CustomException {
        Optional<Gender> foundGender = genderRepository.findById(genderId);
        if (foundGender.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundGender.get();
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

    private User getUserValid(String username) throws CustomException {
        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByUsernameAndUserStatus(username, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }
}
