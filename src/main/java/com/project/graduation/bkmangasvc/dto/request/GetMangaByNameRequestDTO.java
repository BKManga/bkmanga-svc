package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaByNameRequestDTO {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String otherName;

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;

    @NotNull
    private String orderBy;
}
