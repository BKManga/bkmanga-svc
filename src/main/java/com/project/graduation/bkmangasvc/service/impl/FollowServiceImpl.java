package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateFollowResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetFollowResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.FollowService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final MangaRepository mangaRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<GetFollowResponseDTO> getFollow(GetFollowRequestDTO getFollowRequestDTO) throws CustomException {
        Manga manga = getMangaValue(getFollowRequestDTO.getMangaId());
        User user = getUserValue(getFollowRequestDTO.getUserId());

        Optional<Follow> follow = followRepository.findByMangaAndUser(manga, user);

        if (follow.isPresent()) {
            return ApiResponse.successWithResult(modelMapper.map(follow.get(), GetFollowResponseDTO.class));
        }

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<CreateFollowResponseDTO> createFollow(
            CreateFollowRequestDTO createFollowRequestDTO
    ) throws CustomException {
        Manga manga = getMangaValue(createFollowRequestDTO.getMangaId());
        User user = getUserValue(createFollowRequestDTO.getUserId());

        Follow follow = new Follow();
        follow.setManga(manga);
        follow.setUser(user);

        followRepository.save(follow);

        return ApiResponse.successWithResult(modelMapper.map(follow, CreateFollowResponseDTO.class));
    }

    @Override
    public ApiResponse<?> deleteFollow(DeleteFollowRequestDTO deleteFollowRequestDTO) throws CustomException {
        Follow follow = getFollowValue(deleteFollowRequestDTO.getId());

        followRepository.delete(follow);

        return ApiResponse.successWithResult(null);
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

    private Follow getFollowValue(Long followId) throws CustomException {
        Optional<Follow> foundFollow = followRepository.findById(followId);

        if (foundFollow.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return foundFollow.get();
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(mangaId);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }
}
