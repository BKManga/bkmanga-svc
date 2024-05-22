package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.service.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/manga")
@CrossOrigin(origins = "*")
public class MangaController {
    private final MangaService mangaService;

    @GetMapping(path = "/{id}")
    public ApiResponse<Manga> getMangaById(@PathVariable Long id) throws CustomException {
        return mangaService.getMangaById(id);
    }
}
