package com.project.graduation.bkmangasvc.model;

import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.Manga;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistoryResponse {

    private Long id;

    private Manga manga;

    private Chapter chapter;
}
