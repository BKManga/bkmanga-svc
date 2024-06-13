package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetOutLawReportByIdRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateOutLawReportRequestDTO;
import com.project.graduation.bkmangasvc.entity.OutLawReport;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.OutLawReportService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/report/outlaw")
@CrossOrigin(origins = "*")
public class OutLawReportController {
    private final OutLawReportService outLawReportService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<OutLawReport>> getOutLawReport(
            @Valid @RequestBody GetListOutLawReportRequestDTO getListOutLawReportRequestDTO
    ) {
        return outLawReportService.getAllOutLawReport(getListOutLawReportRequestDTO);
    }

    @PostMapping(path = "/detail")
    public ApiResponse<OutLawReport> getOutLawReportDetail(
            @Valid @RequestBody GetOutLawReportByIdRequestDTO getOutLawReportByIdRequestDTO
    ) throws CustomException {
        return outLawReportService.getOutLawReportById(getOutLawReportByIdRequestDTO);
    }


    @PostMapping("/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<OutLawReport> createOutLawReport(
            @Valid @RequestBody CreateOutLawReportRequestDTO createOutLawReportRequestDTO
    ) throws CustomException {
        return outLawReportService.createOutLawReport(createOutLawReportRequestDTO);
    }


    @PostMapping(path = "/update")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<OutLawReport> updateOutLawReport(
            @Valid @RequestBody UpdateOutLawReportRequestDTO updateOutLawReportRequestDTO
    ) throws CustomException {
        return outLawReportService.updateOutLawReport(updateOutLawReportRequestDTO);
    }

}
