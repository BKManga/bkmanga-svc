package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateOrEditHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetListHistoryResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.HistoryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/history")
@CrossOrigin(origins = "*")
public class HistoryController {
    private final HistoryService historyService;

    @PostMapping(path = "/all")
    public ApiResponse<GetListHistoryResponseDTO> getAllHistoryByUser(
            GetListHistoryRequestDTO getListHistoryRequestDTO
    ) throws CustomException {
        return historyService.getAllHistoryByUser(getListHistoryRequestDTO);
    }

    @PostMapping(path = "/createOrUpdate")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> createOrEditHistory(
            CreateOrEditHistoryRequestDTO createOrEditHistoryRequestDTO
    ) throws CustomException {
        return historyService.createOrEditHistory(createOrEditHistoryRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteHistory(
            DeleteHistoryRequestDTO deleteHistoryRequestDTO
    ) throws CustomException {
        return historyService.deleteHistory(deleteHistoryRequestDTO);
    }
}
