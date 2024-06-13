package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateErrorChapterReportRequestDTO {

    @NotNull
    private Long errorChapterReportId;

    @NotNull
    private Integer errorReportStatusId;

    @NotNull
    private Long updatedById;
}
