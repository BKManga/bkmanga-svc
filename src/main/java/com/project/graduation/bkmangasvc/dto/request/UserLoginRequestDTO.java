package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserLoginRequestDTO {

    @NotNull
    @NotBlank
    private String loginID;

    @NotNull
    @NotBlank
    private String password;
}
