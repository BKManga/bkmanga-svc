package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}