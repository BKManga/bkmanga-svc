package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.entity.Author;
import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.GenreService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/genre")
@CrossOrigin(origins = "*")
public class GenreController {
    final private GenreService genreService;

    @GetMapping(path = "/all")
    public ApiResponse<List<Genre>> getAllGenre() {
        return genreService.getAllGenre();
    }

    @GetMapping(path = "/{id}")
    public ApiResponse<Genre> getGenreById(@PathVariable Integer id) throws CustomException {
        return genreService.getGenreById(id);
    }

    @PostMapping(path = "/create")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Genre> createGenre(
            @Valid @RequestBody CreateGenreRequestDTO createGenreRequestDTO
    ) throws CustomException {
        return genreService.createGenre(createGenreRequestDTO);
    }

    @PutMapping(path = "/update")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<Genre> updateGenre(
            @Valid @RequestBody UpdateGenreRequestDTO updateGenreRequestDTO
    ) throws CustomException {
        return genreService.updateGenre(updateGenreRequestDTO);
    }

    @DeleteMapping(path = "/delete")
    @Transactional(rollbackOn = CustomException.class)
    public ApiResponse<?> deleteGenre(
            @Valid @RequestBody DeleteGenreRequestDTO deleteGenreRequestDTO
    ) throws CustomException {
        return genreService.deleteGenre(deleteGenreRequestDTO);
    }
}