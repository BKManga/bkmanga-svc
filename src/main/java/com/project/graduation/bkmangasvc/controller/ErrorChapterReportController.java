package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetErrorChapterReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateErrorChapterReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.ErrorChapterReport;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.ErrorChapterReportService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/report/chapter")
@CrossOrigin(origins = "*")
public class ErrorChapterReportController {
    private final ErrorChapterReportService errorChapterReportService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<ErrorChapterReport>> getListErrorChapterReport(
            @Valid @RequestBody GetListErrorChapterReportRequestDTO getListErrorChapterReportRequestDTO)
    {
        return errorChapterReportService.getErrorChapterReport(getListErrorChapterReportRequestDTO);
    }

    @PostMapping(path = "/detail")
    public ApiResponse<ErrorChapterReport> getErrorChapterReportDetail(
            @Valid @RequestBody GetErrorChapterReportByIdRequestDTO getErrorChapterReportByIdRequestDTO
    ) throws CustomException {
        return errorChapterReportService.getErrorChapterReportById(getErrorChapterReportByIdRequestDTO);
    }

    @PostMapping("/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<ErrorChapterReport> createChapterReport(
            @Valid @RequestBody CreateErrorChapterReportRequestDTO createErrorChapterReportRequestDTO
    ) throws CustomException {
        return errorChapterReportService.createErrorChapterReport(createErrorChapterReportRequestDTO);
    }

    @PutMapping(path = "/update")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<ErrorChapterReport> updateChapterReport(
            @Valid @RequestBody UpdateErrorChapterReportRequestDTO updateErrorChapterReportRequestDTO
    ) throws CustomException {
        return errorChapterReportService.updateErrorChapterReport(updateErrorChapterReportRequestDTO);
    }
}
