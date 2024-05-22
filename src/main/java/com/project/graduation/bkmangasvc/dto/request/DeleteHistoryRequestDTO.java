package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DeleteHistoryRequestDTO {

    @NotNull
    private Long id;
}
