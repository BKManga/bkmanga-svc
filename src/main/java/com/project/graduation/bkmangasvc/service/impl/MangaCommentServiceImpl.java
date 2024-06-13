package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.SortingOrderBy;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateMangaCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.MangaComment;
import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.MangaCommentRepository;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.MangaCommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MangaCommentServiceImpl implements MangaCommentService {

    private final MangaCommentRepository mangaCommentRepository;
    private final MangaRepository mangaRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<Page<MangaComment>> getMangaCommentByMangaId(
            GetListMangaCommentRequestDTO mangaCommentListRequestDTO
    )
        throws CustomException
    {
        Manga foundManga = getMangaValue(mangaCommentListRequestDTO.getMangaId());

        Pageable pageable = PageRequest.of(
                mangaCommentListRequestDTO.getPage(),
                mangaCommentListRequestDTO.getSize(),
                getSorting("createdAt", mangaCommentListRequestDTO.getOrderBy())
        );

        Page<MangaComment> mangaCommentPage = mangaCommentRepository.findMangaCommentByManga(foundManga, pageable);

        return ApiResponse.successWithResult(mangaCommentPage);
    }

    @Override
    public ApiResponse<CreateMangaCommentResponseDTO> createMangaComment(
            CreateMangaCommentRequestDTO mangaCommentCreateRequestDTO
    ) throws CustomException {
        Manga foundManga = getMangaValue(mangaCommentCreateRequestDTO.getMangaId());
        User foundUser = getUserValue(mangaCommentCreateRequestDTO.getUserId());

        MangaComment mangaComment = new MangaComment();

        mangaComment.setManga(foundManga);
        mangaComment.setContent(mangaCommentCreateRequestDTO.getContent());
        mangaComment.setUser(foundUser);

        mangaCommentRepository.save(mangaComment);

        return ApiResponse.successWithResult(modelMapper.map(mangaComment, CreateMangaCommentResponseDTO.class));
    }

    @Override
    public ApiResponse<?> deleteMangaComment(
            DeleteMangaCommentRequestDTO deleteMangaCommentRequestDTO
    ) throws CustomException {
        MangaComment mangaComment = getMangaCommentValue(
                deleteMangaCommentRequestDTO.getMangaCommentId(),
                deleteMangaCommentRequestDTO.getUserId()
        );

        mangaCommentRepository.delete(mangaComment);

        return ApiResponse.successWithResult(null);
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(mangaId);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }

    private User getUserValue(Long userId) throws CustomException {
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

    private MangaComment getMangaCommentValue(Long id, Long userId) throws CustomException {
        User user = getUserValue(userId);

        Optional<MangaComment> foundMangaComment = mangaCommentRepository.findMangaCommentByIdAndUser(id, user);

        if (foundMangaComment.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_COMMENT_NOT_EXIST);
        }

        return foundMangaComment.get();
    }

    private Sort getSorting(String sortField, String sortType) {
        if (sortType.equalsIgnoreCase(SortingOrderBy.DESC.getOrderBy())) {
            return Sort.by(sortField).descending();
        }
        return Sort.by(sortField).ascending();
    }
}