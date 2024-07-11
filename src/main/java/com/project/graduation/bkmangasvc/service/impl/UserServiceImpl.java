package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserRole;
import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetUserManagementResponseDTO;
import com.project.graduation.bkmangasvc.entity.Gender;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.GenderRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final PasswordEncoder passwordEncoder;
    private final GenderRepository genderRepository;
    private final ModelMapper modelMapper;

    @Override
    public User findByUsername(String username) throws CustomException {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }

    @Override
    public ApiResponse<Page<GetUserManagementResponseDTO>> getUserList(GetUserListRequestDTO getUserListRequestDTO) {

        Pageable pageable = PageRequest.of(
                getUserListRequestDTO.getPage(),
                getUserListRequestDTO.getSize()
        );

        Page<User> userPage = userRepository.findAll(pageable);

        Page<GetUserManagementResponseDTO> getUserDetailRequestDTOPage = userPage.map(
                this::getUserManagementResponseDTOValue
        );

        return ApiResponse.successWithResult(getUserDetailRequestDTOPage);
    }

    @Override
    public ApiResponse<?> updateStatusUser(
            UpdateStatusUserRequestDTO updateStatusUserRequestDTO
    ) throws CustomException {

        User user = getUserValue(updateStatusUserRequestDTO.getUserId());
        UserStatus userStatus = getUserStatusValue(updateStatusUserRequestDTO.getUserStatusId());

        user.setUserStatus(userStatus);
        userRepository.save(user);

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<GetUserManagementResponseDTO> getUserDetail(
            GetUserDetailRequestDTO getUserDetailRequestDTO
    ) throws CustomException {
        User user = getUserValue(getUserDetailRequestDTO.getUserId());

        GetUserManagementResponseDTO getUserManagementResponseDTO = getUserManagementResponseDTOValue(user);
        return ApiResponse.successWithResult(getUserManagementResponseDTO);
    }

    @Override
    public ApiResponse<?> updateInfoUser(UpdateInfoUserRequestDTO updateInfoUserRequestDTO) throws CustomException {
        User user = getUserValue(updateInfoUserRequestDTO.getUserId());
        Gender gender = getGenderValue(updateInfoUserRequestDTO.getGenderId());

        user.setGender(gender);
        user.setFullName(updateInfoUserRequestDTO.getFullName());
        user.setDateOfBirth(updateInfoUserRequestDTO.getDateOfBirth());

        return ApiResponse.successWithResult(user);
    }

    @Override
    public ApiResponse<?> updatePasswordUser(
            UpdatePasswordUserRequestDTO updatePasswordUserRequestDTO
    ) throws CustomException {
        User user = getUserValue(updatePasswordUserRequestDTO.getUserId());

        if (!user.getRole().contains(UserRole.ADMIN.getCode())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return ApiResponse.successWithResult(null);
        }

        throw new CustomException(ErrorCode.UNKNOWN_ERROR);
    }

    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        User user = optionalUser.get();
        List<GrantedAuthority> authorityList = new ArrayList<>();

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        authorityList.add(authority);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorityList
        );
    }

    private User getUserValue(Long userId) throws CustomException {
        Optional<User> foundUser = userRepository.findById(userId);

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }

    private UserStatus getUserStatusValue(Integer userStatusId) throws CustomException {
        Optional<UserStatus> foundUserStatus = userStatusRepository.findById(userStatusId);

        if (foundUserStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundUserStatus.get();
    }

    private Gender getGenderValue(Integer genderId) throws CustomException {
        Optional<Gender> foundGender = genderRepository.findById(genderId);

        if (foundGender.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundGender.get();
    }

    private GetUserManagementResponseDTO getUserManagementResponseDTOValue(User user) {
        return modelMapper.map(user, GetUserManagementResponseDTO.class);
    }
}
