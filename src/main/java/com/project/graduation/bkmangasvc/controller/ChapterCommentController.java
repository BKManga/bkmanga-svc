package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetChapterCommentDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateChapterCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.ChapterComment;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.ChapterCommentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/comment/chapter")
@CrossOrigin(origins = "*")
public class ChapterCommentController {
    private final ChapterCommentService chapterCommentService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<ChapterComment>> getChapterCommentList(
            @Valid @RequestBody GetListChapterCommentRequestDTO getListChapterCommentRequestDTO
    ) throws CustomException {
        return chapterCommentService.getChapterCommentByMangaId(getListChapterCommentRequestDTO);
    }

    @PostMapping(path = "/detail")
    public ApiResponse<ChapterComment> getChapterCommentDetail(
            @Valid @RequestBody GetChapterCommentDetailRequestDTO getChapterCommentDetailRequestDTO
    ) throws CustomException {
        return chapterCommentService.getChapterCommentDetail(getChapterCommentDetailRequestDTO);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<CreateChapterCommentResponseDTO> createChapterComment(
            @Valid @RequestBody CreateChapterCommentRequestDTO createChapterCommentRequestDTO
    ) throws CustomException {
        return chapterCommentService.createChapterComment(createChapterCommentRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteChapterComment(
            @Valid @RequestBody DeleteChapterCommentRequestDTO deleteChapterCommentRequestDTO
    ) throws CustomException {
        return chapterCommentService.deleteChapterComment(deleteChapterCommentRequestDTO);
    }
}
