package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetListMangaCommentRequestDTO {
    @NotNull
    Long mangaId;

    @NotNull
    Integer page;

    @NotNull
    Integer size;

    @NotNull
    @NotBlank
    String orderBy;
}
