package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaTopResponseDTO;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.MangaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/manga")
@CrossOrigin(origins = "*")
public class MangaController {
    private final MangaService mangaService;

    @GetMapping(path = "/get/top")
    public ApiResponse<List<GetMangaTopResponseDTO>> getTopManga() {
        return mangaService.getListTopManga();
    }

    @PostMapping(path = "/get/lastUpload")
    public ApiResponse<Page<GetMangaResponseDTO>> getMangaListByLastUploadChapter(
            @Valid @RequestBody GetMangaListByUploadChapterRequestDTO getMangaListByUploadChapterRequestDTO
    ) {
        return mangaService.getMangaListByLastUploadChapter(getMangaListByUploadChapterRequestDTO);
    }

    @PostMapping(path = "/detail")
    public ApiResponse<GetMangaResponseDTO> getMangaDetail(
            @Valid @RequestBody GetMangaRequestDTO getMangaRequestDTO
    ) throws CustomException {
        return mangaService.getMangaDetail(getMangaRequestDTO);
    }

    @PostMapping(path = "/search/by/name")
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByName(
            @Valid @RequestBody GetMangaByNameRequestDTO getMangaByNameRequestDTO
            ) {
        return mangaService.searchMangaByName(getMangaByNameRequestDTO);
    }

    @PostMapping(path = "/search/by/genre")
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByGenre(
            @Valid @RequestBody GetMangaByGenreRequestDTO getMangaByGenreRequestDTO
    ) throws CustomException {
        return mangaService.searchMangaByGenre(getMangaByGenreRequestDTO);
    }

    @PostMapping(path = "search/by/author")
    public ApiResponse<List<GetMangaResponseDTO>> searchMangaByAuthor(
            @Valid @RequestBody GetMangaByAuthorDTO getMangaByAuthorDTO
    ) throws CustomException {
        return mangaService.searchMangaByAuthor(getMangaByAuthorDTO);
    }

    @PostMapping(path = "/search/by/filter")
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByFilter(
            @Valid @RequestBody GetMangaByFilterRequestDTO getMangaByFilterRequestDTO
    ) throws CustomException {
        return mangaService.searchMangaByFilter(getMangaByFilterRequestDTO);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Manga> createManga(
            @Valid @RequestBody CreateMangaRequestDTO createMangaRequestDTO
    ) throws CustomException {
        return mangaService.createManga(createMangaRequestDTO);
    }

    @PutMapping(path = "/update")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Manga> updateManga(
            @Valid @RequestBody UpdateMangaRequestDTO updateMangaRequestDTO
    ) throws CustomException {
        return null;
    }
}
