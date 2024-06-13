package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateErrorChapterReportRequestDTO {

    @NotNull
    private Long mangaId;

    @NotNull
    private Long chapterId;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Long uploadedById;

    @NotNull
    private Integer errorTypeId;
}
