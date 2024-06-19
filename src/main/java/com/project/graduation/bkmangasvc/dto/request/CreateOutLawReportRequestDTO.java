package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateOutLawReportRequestDTO {

    @NotNull
    private Long userReportedId;

    private Long commentReportedId;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private Integer outLawTypeId;

    @NotNull
    private Integer outLawAreaId;
}
