package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateOrEditHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetListHistoryResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface HistoryService {

    ApiResponse<GetListHistoryResponseDTO> getAllHistoryByUser() throws CustomException;

    ApiResponse<?> createOrEditHistory(
            CreateOrEditHistoryRequestDTO createOrEditHistoryRequestDTO
    ) throws CustomException;

    ApiResponse<?> deleteHistory(
            DeleteHistoryRequestDTO deleteHistoryRequestDTO
    ) throws CustomException;
}
