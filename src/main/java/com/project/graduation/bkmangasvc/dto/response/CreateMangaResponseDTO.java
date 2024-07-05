package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.Manga;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateMangaResponseDTO {

    private Manga createdManga;

    private Chapter createdChapter;
}
