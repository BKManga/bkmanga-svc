package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateMangaRequestDTO {

    @NotNull
    private Long mangaId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String otherName;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotEmpty
    private List<Integer> listGenreId;

    @NotNull
    @NotEmpty
    private List<Integer> listAuthorId;

    @NotNull
    private Integer ageRangeId;

    @NotNull
    private Integer mangaStatusId;
}
