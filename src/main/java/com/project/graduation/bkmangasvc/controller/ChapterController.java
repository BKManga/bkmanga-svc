package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.GetListChapterRequestDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.ChapterService;
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

    @PostMapping(path = "/get")
    public ApiResponse<List<Chapter>> getChapterByManga(
            @Valid @RequestBody GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException {
        return chapterService.getChapterByManga(getListChapterRequestDTO);
    }

//    @PostMapping(path = "/get/detail")
//    public ApiResponse<List<Chapter>> getChapterDetail(
//            @RequestBody GetListChapterRequestDTO getListChapterRequestDTO
//    ) throws CustomException {
//        return chapterService.getChapterDetail(getListChapterRequestDTO);
//    }
}