package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaByGenreRequestDTO {

    @NotNull
    private Integer genreId;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @NotNull
    private String orderBy;
}
