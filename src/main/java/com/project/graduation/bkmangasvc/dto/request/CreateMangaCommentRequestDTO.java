package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMangaCommentRequestDTO {

    @NotNull
    @NotBlank
    private String content;

    @NotNull
    private Long mangaId;
}
