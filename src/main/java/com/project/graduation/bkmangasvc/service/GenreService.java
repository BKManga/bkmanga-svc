package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.dto.request.GetListMangaByGenreRequestDTO;
import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface GenreService {
    ApiResponse<List<Genre>> getAllGenre();

    ApiResponse<Genre> getGenreById(Integer id) throws CustomException;

//    ApiResponse<Genre> createGenre(Genre genre) throws CustomException;
//
//    ApiResponse<Genre> updateGenre(Genre genre) throws CustomException;
//
//    ApiResponse<Genre> deleteGenre(Integer id) throws CustomException;
}
