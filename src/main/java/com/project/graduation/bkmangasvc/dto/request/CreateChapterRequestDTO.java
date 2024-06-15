package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateChapterRequestDTO {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Long uploadedById;

    @NotNull
    private Long mangaId;
}
