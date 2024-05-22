package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateLikeMangaRequestDTO {

    @NotNull
    private Long mangaId;

    @NotNull
    private Long userId;
}
