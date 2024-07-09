package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.OutLawAreaEnum;
import com.project.graduation.bkmangasvc.constant.OutLawProcessStatusEnum;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetOutLawReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.OutLawReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OutLawReportServiceImpl implements OutLawReportService {

    private final OutLawReportRepository outLawReportRepository;
    private final UserRepository userRepository;
    private final OutLawTypeRepository outLawTypeRepository;
    private final OutLawProcessStatusRepository outLawProcessStatusRepository;
    private final OutLawAreaRepository outLawAreaRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public ApiResponse<Page<OutLawReport>> getAllOutLawReport(
            GetListOutLawReportRequestDTO getListOutLawReportRequestDTO
    ) throws CustomException {
        Pageable pageable = PageRequest.of(
                getListOutLawReportRequestDTO.getPage(),
                getListOutLawReportRequestDTO.getSize()
        );

//        OutLawProcessStatus outLawProcessStatus = getOutLawProcessStatusValue(OutLawProcessStatusEnum.NEW.getCode());

        Page<OutLawReport> outLawReportPage = outLawReportRepository.findByOrderByCreatedAtDesc(pageable);

        return ApiResponse.successWithResult(outLawReportPage);
    }

    @Override
    public ApiResponse<OutLawReport> getOutLawReportById(
            GetOutLawReportByIdRequestDTO getOutLawReportByIdRequestDTO
    ) throws CustomException {

        OutLawReport outLawReport = getOutLawReportValue(getOutLawReportByIdRequestDTO.getId());

        return ApiResponse.successWithResult(outLawReport);
    }

    @Override
    public ApiResponse<OutLawReport> createOutLawReport(
            CreateOutLawReportRequestDTO createOutLawReportRequestDTO
    ) throws CustomException {

        User userReported = getUserValue(createOutLawReportRequestDTO.getUserReportedId());
        User uploadedBy = getUserValue(TokenHelper.getPrincipal());

        OutLawType outLawType = getOutLawTypeValue(createOutLawReportRequestDTO.getOutLawTypeId());
        OutLawArea outLawArea = getOutLawAreaValue(createOutLawReportRequestDTO.getOutLawAreaId());
        OutLawProcessStatus outLawProcessStatus = getOutLawProcessStatusValue(OutLawProcessStatusEnum.NEW.getCode());

        OutLawReport outLawReport = new OutLawReport();

        outLawReport.setDescription(createOutLawReportRequestDTO.getDescription());
        outLawReport.setUserReported(userReported);
        outLawReport.setUploadedBy(uploadedBy);
        outLawReport.setOutLawType(outLawType);
        outLawReport.setOutLawArea(outLawArea);

        if (Objects.equals(outLawArea.getId(), OutLawAreaEnum.COMMENT_MANGA.getCode())
                || Objects.equals(outLawArea.getId(), OutLawAreaEnum.COMMENT_CHAPTER.getCode())) {
            outLawReport.setCommentReported(createOutLawReportRequestDTO.getCommentReportedId());
        }

        outLawReport.setOutLawProcessStatus(outLawProcessStatus);

        outLawReportRepository.save(outLawReport);

        return ApiResponse.successWithResult(outLawReport);
    }

    @Override
    public ApiResponse<OutLawReport> updateOutLawReport(
            UpdateOutLawReportRequestDTO updateOutLawReportRequestDTO
    ) throws CustomException {

        OutLawReport outLawReport = getOutLawReportValue(updateOutLawReportRequestDTO.getOutLawReportId());
        OutLawProcessStatus outLawProcessStatus = getOutLawProcessStatusValue(
                updateOutLawReportRequestDTO.getOutLawReportProcessStatus()
        );

        User userUpdate = getUserValue(TokenHelper.getPrincipal());

        outLawReport.setOutLawProcessStatus(outLawProcessStatus);
        outLawReport.setUpdatedBy(userUpdate);
        outLawReportRepository.save(outLawReport);

        return ApiResponse.successWithResult(outLawReport);
    }

    private OutLawReport getOutLawReportValue(Long idOutLawReport) throws CustomException {
        Optional<OutLawReport> foundOutLawReport = outLawReportRepository.findById(idOutLawReport);

        if (foundOutLawReport.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return foundOutLawReport.get();
    }

    private User getUserValue (Long idUser) throws CustomException {

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

    private OutLawType getOutLawTypeValue(Integer idOutLawType) throws CustomException {
        Optional<OutLawType> foundOutLawType = outLawTypeRepository.findById(idOutLawType);

        if (foundOutLawType.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundOutLawType.get();
    }

    private OutLawArea getOutLawAreaValue(Integer idOutLawArea) throws CustomException {
        Optional<OutLawArea> foundOutLawArea = outLawAreaRepository.findById(idOutLawArea);

        if (foundOutLawArea.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundOutLawArea.get();
    }

    private OutLawProcessStatus getOutLawProcessStatusValue(Integer outLawProcessStatusId) throws CustomException {
        Optional<OutLawProcessStatus> foundOutLawProcessStatus = outLawProcessStatusRepository
                .findById(outLawProcessStatusId);

        if (foundOutLawProcessStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundOutLawProcessStatus.get();
    }
}
