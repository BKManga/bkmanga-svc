package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.GetLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.CreateLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteLikeMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateLikeMangaResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.LikeMangaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/like")
@CrossOrigin(origins = "*")
public class LikeController {
    private final LikeMangaService likeMangaService;

    @PostMapping(path = "/get")
    public ApiResponse<GetLikeMangaResponseDTO> getLikeManga(
            @Valid @RequestBody GetLikeMangaRequestDTO getLikeMangaRequestDTO
    ) throws CustomException {
        return likeMangaService.getLikeManga(getLikeMangaRequestDTO);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<CreateLikeMangaResponseDTO> createLikeManga(
            @Valid @RequestBody CreateLikeMangaRequestDTO createLikeMangaRequestDTO
    ) throws CustomException {
        return likeMangaService.createLikeManga(createLikeMangaRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteLikeManga(
            @Valid @RequestBody DeleteLikeMangaRequestDTO deleteLikeMangaRequestDTO
    ) throws CustomException {
        return likeMangaService.deleteLikeManga(deleteLikeMangaRequestDTO);
    }
}
