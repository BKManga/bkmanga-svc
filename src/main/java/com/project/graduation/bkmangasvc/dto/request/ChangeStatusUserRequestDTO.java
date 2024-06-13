package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChangeStatusUserRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Integer userStatusId;
}
