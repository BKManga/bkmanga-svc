package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequestDTO {

    @NotBlank
    @NotNull
    private String username;

    @NotBlank
    @NotNull
    private String fullName;

    @NotBlank
    @NotNull
    private String role;

    @NotNull
    private Integer gender;

    @NotBlank
    @NotNull
    private String password;

    @NotBlank
    @NotNull
    private String email;

    @NotBlank
    @NotNull
    private String dateOfBirth;

    @NotBlank
    @NotNull
    private String phoneNumber;
}
