package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaTopResponseDTO;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MangaService {

    ApiResponse<List<GetMangaTopResponseDTO>> getListTopManga();

    ApiResponse<Page<GetMangaResponseDTO>> getMangaListByLastUploadChapter(
            GetMangaListByUploadChapterRequestDTO getMangaListByUploadChapterRequestDTO
    );

    ApiResponse<Page<GetMangaResponseDTO>> getMangaList (GetMangaRequestDTO getMangaRequestDTO);

    ApiResponse<GetMangaResponseDTO> getMangaDetail(
            GetMangaDetailRequestDTO getMangaDetailRequestDTO
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

    ApiResponse<Manga> createManga(CreateMangaRequestDTO createMangaRequestDTO) throws CustomException;

    ApiResponse<Manga> updateManga(UpdateMangaRequestDTO updateMangaRequestDTO) throws CustomException;
}
