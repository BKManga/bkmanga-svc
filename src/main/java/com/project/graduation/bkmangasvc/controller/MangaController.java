package com.project.graduation.bkmangasvc.controller;

import com.project.graduation.bkmangasvc.service.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/manga")
@CrossOrigin(origins = "*")
public class MangaController {
    private final MangaService mangaService;
}
