package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateGenreRequestDTO {

    @NotNull
    private Integer genreId;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String description;
}
