package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaByFilterRequestDTO {

    @NotNull
    private List<Integer> genreApproveList;

    @NotNull
    private List<Integer> genreDenyList;

    @NotNull
    private Integer mangaStatus;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @NotNull
    @NotBlank
    private String orderBy;
}
