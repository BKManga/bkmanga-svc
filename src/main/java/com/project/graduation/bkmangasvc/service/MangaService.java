package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;

public interface MangaService {
    ApiResponse<Manga> getMangaById(Long mangaId) throws CustomException;
}
