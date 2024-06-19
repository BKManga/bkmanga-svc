package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetErrorChapterReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.ErrorChapterReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ErrorChapterReportServiceImpl implements ErrorChapterReportService {

    private final UserRepository userRepository;
    private final ChapterReportRepository chapterReportRepository;
    private final ChapterRepository chapterRepository;
    private final ErrorReportStatusRepository errorReportStatusRepository;
    private final ErrorTypeRepository errorTypeRepository;
    private final MangaRepository mangaRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public ApiResponse<Page<ErrorChapterReport>> getErrorChapterReport(
            GetListErrorChapterReportRequestDTO getListErrorChapterReportRequestDTO
    ) {
        Pageable pageable = PageRequest.of(
                getListErrorChapterReportRequestDTO.getPage(),
                getListErrorChapterReportRequestDTO.getSize()
        );

        Page<ErrorChapterReport> errorChapterReportPage = chapterReportRepository.findByOrderByCreatedAtDesc(pageable);

        return ApiResponse.successWithResult(errorChapterReportPage);
    }

    @Override
    public ApiResponse<ErrorChapterReport> getErrorChapterReportById(
            GetErrorChapterReportByIdRequestDTO getErrorChapterReportByIdRequestDTO
    ) throws CustomException {
        ErrorChapterReport errorChapterReport = getErrorChapterReportValue(
                getErrorChapterReportByIdRequestDTO.getIdErrorChapterReport()
        );

        return ApiResponse.successWithResult(errorChapterReport);
    }

    @Override
    public ApiResponse<ErrorChapterReport> createErrorChapterReport(
            CreateErrorChapterReportRequestDTO createErrorChapterReportRequestDTO
    ) throws CustomException {

        Manga manga = getMangaValue(createErrorChapterReportRequestDTO.getMangaId());
        Chapter chapter = getChapterValue(createErrorChapterReportRequestDTO.getChapterId());

        User userUpload = getUserValue(TokenHelper.getPrincipal());
        ErrorType errorType = getErrorTypeValue(createErrorChapterReportRequestDTO.getErrorTypeId());

        ErrorChapterReport errorChapterReport = new ErrorChapterReport();
        errorChapterReport.setManga(manga);
        errorChapterReport.setChapter(chapter);
        errorChapterReport.setErrorType(errorType);
        errorChapterReport.setDescription(createErrorChapterReportRequestDTO.getDescription());
        errorChapterReport.setUploadedBy(userUpload);

        chapterReportRepository.save(errorChapterReport);

        return ApiResponse.successWithResult(errorChapterReport);
    }

    @Override
    public ApiResponse<ErrorChapterReport> updateErrorChapterReport(
            UpdateErrorChapterReportRequestDTO updateErrorChapterReportRequestDTO
    ) throws CustomException {

        ErrorChapterReport errorChapterReport = getErrorChapterReportValue(
                updateErrorChapterReportRequestDTO.getErrorChapterReportId()
        );

        User userUpdate = getUserValue(TokenHelper.getPrincipal());

        ErrorReportStatus errorReportStatus = getErrorReportStatusValue(
                updateErrorChapterReportRequestDTO.getErrorReportStatusId()
        );

        errorChapterReport.setUpdatedBy(userUpdate);
        errorChapterReport.setErrorReportStatus(errorReportStatus);
        errorReportStatusRepository.save(errorReportStatus);

        return ApiResponse.successWithResult(errorChapterReport);
    }

    private ErrorChapterReport getErrorChapterReportValue(Long idErrorChapterReport) throws CustomException {
        Optional<ErrorChapterReport> errorChapterReportFound = chapterReportRepository.findById(idErrorChapterReport);

        if (errorChapterReportFound.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return errorChapterReportFound.get();
    }

    private ErrorReportStatus getErrorReportStatusValue(Integer idErrorReportStatus) throws  CustomException {
        Optional<ErrorReportStatus> foundErrorReportStatus = errorReportStatusRepository.findById(idErrorReportStatus);

        if (foundErrorReportStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundErrorReportStatus.get();
    }

    private User getUserValue(Long userId) throws CustomException {
        Optional<UserStatus> foundUserStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (foundUserStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, foundUserStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundUser.get();
    }

    private Manga getMangaValue(Long idManga) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(idManga);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }

    private Chapter getChapterValue(Long idChapter) throws CustomException {
        Optional<Chapter> foundChapter = chapterRepository.findById(idChapter);
        if (foundChapter.isEmpty()) {
            throw new CustomException(ErrorCode.CHAPTER_NOT_EXIST);
        }

        return foundChapter.get();
    }

    private ErrorType getErrorTypeValue(Integer idErrorType) throws CustomException {
        Optional<ErrorType> foundErrorType = errorTypeRepository.findById(idErrorType);

        if (foundErrorType.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundErrorType.get();
    }
}
