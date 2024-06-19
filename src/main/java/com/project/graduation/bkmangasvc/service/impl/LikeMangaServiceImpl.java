package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.GetLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.CreateLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.LikeManga;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.LikeMangaRepository;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.LikeMangaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeMangaServiceImpl implements LikeMangaService {

    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final MangaRepository mangaRepository;
    private final LikeMangaRepository likeMangaRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<GetLikeMangaResponseDTO> getLikeManga(
            GetLikeMangaRequestDTO getLikeMangaRequestDTO
    ) throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());
        Manga manga = getMangaValue(getLikeMangaRequestDTO.getMangaId());

        Optional<LikeManga> likeManga = likeMangaRepository.findByMangaAndUser(manga, user);

        if (likeManga.isPresent()) {
            return ApiResponse.successWithResult(modelMapper.map(likeManga.get(), GetLikeMangaResponseDTO.class));
        }

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<CreateLikeMangaResponseDTO> createLikeManga(
            CreateLikeMangaRequestDTO createLikeMangaRequestDTO
    ) throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());
        Manga manga = getMangaValue(createLikeMangaRequestDTO.getMangaId());

        LikeManga likeManga = new LikeManga();

        likeManga.setManga(manga);
        likeManga.setUser(user);

        likeMangaRepository.save(likeManga);

        return ApiResponse.successWithResult(modelMapper.map(likeManga, CreateLikeMangaResponseDTO.class));
    }

    @Override
    public ApiResponse<?> deleteLikeManga(
            DeleteLikeMangaRequestDTO deleteLikeMangaRequestDTO
    ) throws CustomException {
        LikeManga likeManga = getLikeMangaValue(deleteLikeMangaRequestDTO.getId());

        likeMangaRepository.delete(likeManga);

        return ApiResponse.successWithResult(null);
    }

    private User getUserValue(Long idUser) throws CustomException{

        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(idUser, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }

    private LikeManga getLikeMangaValue(Long idLike) throws CustomException {
        Optional<LikeManga> foundLikeManga = likeMangaRepository.findById(idLike);

        if (foundLikeManga.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return foundLikeManga.get();
    }

    private Manga getMangaValue(Long idManga) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(idManga);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }
}
