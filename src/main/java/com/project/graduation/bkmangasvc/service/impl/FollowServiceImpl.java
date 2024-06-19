package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetFollowByMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateFollowResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetFollowByMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.FollowService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ApiResponse<Page<GetMangaResponseDTO>> getListFollowByUser(
            GetListFollowRequestDTO getListFollowRequestDTO
    ) throws CustomException {

        User user = getUserValue(TokenHelper.getPrincipal());

        Pageable pageable = PageRequest.of(
                getListFollowRequestDTO.getPage(),
                getListFollowRequestDTO.getSize()
        );

        Page<Follow> followListPage = followRepository.findByUserOrderByCreatedAtDesc(user, pageable);

        Page<GetMangaResponseDTO> mangaResponseDTOPage = getPageMangaResponseDTO(followListPage);

        return ApiResponse.successWithResult(mangaResponseDTOPage);
    }

    @Override
    public ApiResponse<GetFollowByMangaResponseDTO> getFollowByManga(
            GetFollowByMangaRequestDTO getFollowByMangaRequestDTO
    ) throws CustomException {

        User user = getUserValue(TokenHelper.getPrincipal());
        Manga manga = getMangaValue(getFollowByMangaRequestDTO.getMangaId());

        Optional<Follow> follow = followRepository.findByMangaAndUser(manga, user);

        if (follow.isPresent()) {
            return ApiResponse.successWithResult(modelMapper.map(follow.get(), GetFollowByMangaResponseDTO.class));
        }

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<CreateFollowResponseDTO> createFollow(
            CreateFollowRequestDTO createFollowRequestDTO
    ) throws CustomException {
        Manga manga = getMangaValue(createFollowRequestDTO.getMangaId());
        User user = getUserValue(TokenHelper.getPrincipal());

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

    private Page<GetMangaResponseDTO> getPageMangaResponseDTO (Page<Follow> followPage) {
        return followPage.map(follow -> getMangaResponseDTO(follow.getManga()));
    }

    private GetMangaResponseDTO getMangaResponseDTO (Manga manga) {
        GetMangaResponseDTO getMangaResponseDTO = modelMapper.map(manga, GetMangaResponseDTO.class);
        getMangaResponseDTO.setNumberOfFollow(manga.getFollowList().size());
        getMangaResponseDTO.setNumberOfLikes(manga.getLikeMangaList().size());

        return getMangaResponseDTO;
    }

}
