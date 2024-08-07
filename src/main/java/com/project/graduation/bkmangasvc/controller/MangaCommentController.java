package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListMangaCommentRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetMangaCommentDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateMangaCommentResponseDTO;
import com.project.graduation.bkmangasvc.entity.MangaComment;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.MangaCommentService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/comment/manga")
@CrossOrigin(origins = "*")
public class MangaCommentController {
    private final MangaCommentService mangaCommentService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<MangaComment>> getMangaCommentList(
            @Valid @RequestBody GetListMangaCommentRequestDTO mangaCommentListRequestDTO
    ) throws CustomException {
        return mangaCommentService.getMangaCommentByMangaId(mangaCommentListRequestDTO);
    }

    @PostMapping(path = "/detail")
    public ApiResponse<MangaComment> getMangaCommentDetail(
            @Valid @RequestBody GetMangaCommentDetailRequestDTO getMangaCommentDetailRequestDTO
    ) throws CustomException {
        return null;
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<CreateMangaCommentResponseDTO> createMangaComment(
            @Valid @RequestBody CreateMangaCommentRequestDTO mangaCommentCreateRequestDTO
    ) throws CustomException {
        return mangaCommentService.createMangaComment(mangaCommentCreateRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteMangaComment(
            @Valid @RequestBody DeleteMangaCommentRequestDTO deleteMangaCommentRequestDTO
    ) throws CustomException {
        return mangaCommentService.deleteMangaComment(deleteMangaCommentRequestDTO);
    }
}
