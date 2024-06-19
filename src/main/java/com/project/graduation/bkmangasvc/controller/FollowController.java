package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.CreateFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetFollowByMangaRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListFollowRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.CreateFollowResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetFollowByMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.FollowService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/follow")
@CrossOrigin(origins = "*")
public class FollowController {
    private final FollowService followService;

    @PostMapping(path = "/get")
    public ApiResponse<Page<GetMangaResponseDTO>> getListFollow(
            @Valid @RequestBody GetListFollowRequestDTO getListFollowRequestDTO
    ) throws CustomException {
        return followService.getListFollowByUser(getListFollowRequestDTO);
    }

    @PostMapping(path = "/get/by/manga")
    public ApiResponse<GetFollowByMangaResponseDTO> getFollow(
            @Valid @RequestBody GetFollowByMangaRequestDTO getFollowByMangaRequestDTO
    ) throws CustomException {
        return followService.getFollowByManga(getFollowByMangaRequestDTO);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<CreateFollowResponseDTO> createFollow(
            @Valid @RequestBody CreateFollowRequestDTO createFollowRequestDTO
    ) throws CustomException {
        return followService.createFollow(createFollowRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteFollow(
            @Valid @RequestBody DeleteFollowRequestDTO deleteFollowRequestDTO
    ) throws CustomException {
        return followService.deleteFollow(deleteFollowRequestDTO);
    }
}
