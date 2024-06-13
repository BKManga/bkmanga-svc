package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.GetListChapterRequestDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface ChapterService {
    ApiResponse<List<Chapter>> getChapterByManga(
            GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException;
}
