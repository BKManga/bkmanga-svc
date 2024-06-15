package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.UpdateStatusUserRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetUserListRequestDTO;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public User findByUsername(String username) throws CustomException {
        Optional<User> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }

    @Override
    public ApiResponse<Page<User>> getUserList(GetUserListRequestDTO getUserListRequestDTO) {

        Pageable pageable = PageRequest.of(
                getUserListRequestDTO.getPage(),
                getUserListRequestDTO.getSize()
        );

        Page<User> userPage = userRepository.findAll(pageable);

        return ApiResponse.successWithResult(userPage);
    }

    @Override
    public ApiResponse<User> updateStatusUser(
            UpdateStatusUserRequestDTO updateStatusUserRequestDTO
    ) throws CustomException {

        User user = getUserValue(updateStatusUserRequestDTO.getUserId());

        UserStatus userStatus = getUserStatusValue(updateStatusUserRequestDTO.getUserStatusId());

        user.setUserStatus(userStatus);

        userRepository.save(user);

        return ApiResponse.successWithResult(user);
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
}
