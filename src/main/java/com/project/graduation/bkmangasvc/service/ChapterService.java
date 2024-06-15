package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetChapterDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetChapterDetailResponseDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface ChapterService {
    ApiResponse<List<Chapter>> getChapterByManga(
            GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException;

    ApiResponse<GetChapterDetailResponseDTO> getChapterDetail(
            GetChapterDetailRequestDTO getChapterDetailRequestDTO
    ) throws CustomException;

    ApiResponse<Chapter> createChapter(
            CreateChapterRequestDTO createChapterRequestDTO
    ) throws CustomException;

    ApiResponse<Chapter> updateChapter(
            UpdateChapterRequestDTO updateChapterRequestDTO
    ) throws CustomException;
}
