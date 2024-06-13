package com.project.graduation.bkmangasvc.model;

import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryResponse {

    private Long id;

    private GetMangaResponseDTO getMangaResponseDTO;

    private Chapter chapter;
}
