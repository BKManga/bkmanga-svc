package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.CreateGenreRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteGenreRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListMangaByGenreRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateGenreRequestDTO;
import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface GenreService {
    ApiResponse<List<Genre>> getAllGenre();

    ApiResponse<Genre> getGenreById(Integer id) throws CustomException;

    ApiResponse<Genre> createGenre(CreateGenreRequestDTO createGenreRequestDTO) throws CustomException;

    ApiResponse<Genre> updateGenre(UpdateGenreRequestDTO updateGenreRequestDTO) throws CustomException;

    ApiResponse<?> deleteGenre(DeleteGenreRequestDTO deleteGenreRequestDTO) throws CustomException;
}
