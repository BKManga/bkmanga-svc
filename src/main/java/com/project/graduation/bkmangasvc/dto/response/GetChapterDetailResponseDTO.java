package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.Manga;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetChapterDetailResponseDTO {

    @NotNull
    private Manga manga;

    @NotNull
    private Chapter chapter;

    private Long previousChapterId;

    private Long nextChapterId;
}
