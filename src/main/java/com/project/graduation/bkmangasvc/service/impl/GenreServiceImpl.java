package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.GenreRepository;
import com.project.graduation.bkmangasvc.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;

    @Override
    public ApiResponse<List<Genre>> getAllGenre() {
        List<Genre> genreList = genreRepository.findAll();
        return ApiResponse.successWithResult(genreList);
    }
}
