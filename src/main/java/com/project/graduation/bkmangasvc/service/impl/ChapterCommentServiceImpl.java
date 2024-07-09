package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.SortingOrderBy;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetChapterCommentDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateChapterCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.ChapterCommentRepository;
import com.project.graduation.bkmangasvc.repository.ChapterRepository;
import com.project.graduation.bkmangasvc.repository.UserRepository;
import com.project.graduation.bkmangasvc.repository.UserStatusRepository;
import com.project.graduation.bkmangasvc.service.ChapterCommentService;
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
public class ChapterCommentServiceImpl implements ChapterCommentService {
    private final ChapterCommentRepository chapterCommentRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<Page<ChapterComment>> getChapterCommentByMangaId(
            GetListChapterCommentRequestDTO getListChapterCommentRequestDTO
    ) throws CustomException {
        Chapter chapter = getChapterValue(getListChapterCommentRequestDTO.getChapterId());

        Pageable pageable = PageRequest.of(
                getListChapterCommentRequestDTO.getPage(),
                getListChapterCommentRequestDTO.getSize(),
                getSorting("createdAt", getListChapterCommentRequestDTO.getOrderBy())
        );

        Page<ChapterComment> mangaCommentPage = chapterCommentRepository.findChapterCommentByChapter(chapter, pageable);

        return ApiResponse.successWithResult(mangaCommentPage);
    }

    @Override
    public ApiResponse<ChapterComment> getChapterCommentDetail(
            GetChapterCommentDetailRequestDTO getChapterCommentDetailRequestDTO
    ) throws CustomException {
        ChapterComment chapterComment = getChapterCommentValue(
                getChapterCommentDetailRequestDTO.getChapterCommentId()
        );

        return ApiResponse.successWithResult(chapterComment);
    }

    @Override
    public ApiResponse<CreateChapterCommentResponseDTO> createChapterComment(
            CreateChapterCommentRequestDTO createChapterCommentRequestDTO
    ) throws CustomException {
        User user = getUserValue(TokenHelper.getPrincipal());
        Chapter chapter = getChapterValue(createChapterCommentRequestDTO.getChapterId());

        ChapterComment chapterComment = new ChapterComment();

        chapterComment.setUser(user);
        chapterComment.setChapter(chapter);
        chapterComment.setContent(createChapterCommentRequestDTO.getContent());
        chapterCommentRepository.save(chapterComment);

        return ApiResponse.successWithResult(modelMapper.map(chapterComment, CreateChapterCommentResponseDTO.class));
    }

    @Override
    public ApiResponse<?> deleteChapterComment(
            DeleteChapterCommentRequestDTO deleteChapterCommentRequestDTO
    ) throws CustomException {
        ChapterComment chapterComment = getChapterCommentValue(
                deleteChapterCommentRequestDTO.getChapterCommentId(),
                TokenHelper.getPrincipal()
        );

        chapterCommentRepository.delete(chapterComment);

        return ApiResponse.successWithResult(null);
    }

    private ChapterComment getChapterCommentValue(Long id, Long userId) throws CustomException {

        User user = getUserValue(userId);

        Optional<ChapterComment> foundChapterComment = chapterCommentRepository.findChapterCommentByIdAndUser(id, user);

        if (foundChapterComment.isEmpty()) {
            throw new CustomException(ErrorCode.CHAPTER_COMMENT_NOT_EXIST);
        }

        return foundChapterComment.get();
    }

    private ChapterComment getChapterCommentValue(Long id) throws CustomException {

        Optional<ChapterComment> foundChapterComment = chapterCommentRepository.findById(id);

        if (foundChapterComment.isEmpty()) {
            throw new CustomException(ErrorCode.CHAPTER_COMMENT_NOT_EXIST);
        }

        return foundChapterComment.get();
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

    private Chapter getChapterValue(Long chapterId) throws CustomException {
        Optional<Chapter> foundChapter = chapterRepository.findById(chapterId);

        if (foundChapter.isEmpty()) {
            throw new CustomException(ErrorCode.CHAPTER_NOT_EXIST);
        }

        return foundChapter.get();
    }

    private Sort getSorting(String sortField, String sortType) {
        if (sortType.equalsIgnoreCase(SortingOrderBy.DESC.getOrderBy())) {
            return Sort.by(sortField).descending();
        }
        return Sort.by(sortField).ascending();
    }
}
