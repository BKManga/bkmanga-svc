package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UpdateInfoUserRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    private Integer genderId;

    @NotNull
    @NotBlank
    private String dateOfBirth;
}
