package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetErrorChapterReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.ErrorChapterReport;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

public interface ErrorChapterReportService {
    ApiResponse<Page<ErrorChapterReport>> getErrorChapterReport(
            GetListErrorChapterReportRequestDTO getListErrorChapterReportRequestDTO
    );

    ApiResponse<ErrorChapterReport> getErrorChapterReportById(
            GetErrorChapterReportByIdRequestDTO getErrorChapterReportByIdRequestDTO
    ) throws CustomException;

    ApiResponse<ErrorChapterReport> createErrorChapterReport(
            CreateErrorChapterReportRequestDTO createErrorChapterReportRequestDTO
    ) throws CustomException;

    ApiResponse<ErrorChapterReport> updateErrorChapterReport(
            UpdateErrorChapterReportRequestDTO updateErrorChapterReportRequestDTO
    ) throws CustomException;
}
