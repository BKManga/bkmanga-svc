package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

import java.util.List;

public interface GenreService {
    ApiResponse<List<Genre>> getAllGenre();
}
