package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetChapterDetailResponseDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.ChapterService;
import com.project.graduation.bkmangasvc.service.FileService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/chapter")
@CrossOrigin(origins = "*")
public class ChapterController {

    private final ChapterService chapterService;
    private final FileService fileService;

    @PostMapping(path = "/get")
    public ApiResponse<List<Chapter>> getChapterByManga(
            @Valid @RequestBody GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException {
        return chapterService.getChapterByManga(getListChapterRequestDTO);
    }

    @PostMapping(path = "/get/detail")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<GetChapterDetailResponseDTO> getChapterDetail(
            @Valid @RequestBody GetChapterDetailRequestDTO getChapterDetailRequestDTO
    ) throws CustomException {
        return chapterService.getChapterDetail(getChapterDetailRequestDTO);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Chapter> createChapter(
            @Valid @RequestBody CreateChapterRequestDTO createChapterRequestDTO
    ) throws CustomException {
        return chapterService.createChapter(createChapterRequestDTO);
    }

    @PutMapping(path = "/update")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Chapter> updateChapter(
            @Valid @RequestBody UpdateChapterRequestDTO updateChapterRequestDTO
    ) throws CustomException {
        return null;
    }
}