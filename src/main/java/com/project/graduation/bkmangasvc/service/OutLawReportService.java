package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetOutLawReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.OutLawReport;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface OutLawReportService {
    ApiResponse<Page<OutLawReport>> getAllOutLawReport(
            GetListOutLawReportRequestDTO getListOutLawReportRequestDTO
    ) throws CustomException;

    ApiResponse<OutLawReport> getOutLawReportById(
            GetOutLawReportByIdRequestDTO getOutLawReportByIdRequestDTO
    ) throws CustomException;

    ApiResponse<OutLawReport> createOutLawReport(
            CreateOutLawReportRequestDTO createOutLawReportRequestDTO
    ) throws CustomException;

    ApiResponse<OutLawReport> updateOutLawReport(
            UpdateOutLawReportRequestDTO updateOutLawReportRequestDTO
    ) throws CustomException;
}
