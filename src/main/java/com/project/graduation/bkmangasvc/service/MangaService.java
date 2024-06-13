package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MangaService {

    ApiResponse<Page<GetMangaResponseDTO>> getMangaListByLastUploadChapter(
            GetMangaListByUploadChapterRequestDTO getMangaListByUploadChapterRequestDTO
    );

    ApiResponse<GetMangaResponseDTO> getMangaDetail(
            GetMangaRequestDTO getMangaRequestDTO
    ) throws CustomException;

    ApiResponse<Page<GetMangaResponseDTO>> searchMangaByName(GetMangaByNameRequestDTO getMangaByNameRequestDTO);

    ApiResponse<Page<GetMangaResponseDTO>> searchMangaByGenre(
            GetMangaByGenreRequestDTO getMangaByGenreRequestDTO
    ) throws CustomException;

    ApiResponse<List<GetMangaResponseDTO>> searchMangaByAuthor(
            GetMangaByAuthorDTO getMangaByAuthorDTO
    ) throws CustomException;

    ApiResponse<Page<GetMangaResponseDTO>> searchMangaByFilter(
            GetMangaByFilterRequestDTO getMangaByFilterRequestDTO
    ) throws CustomException;
}
