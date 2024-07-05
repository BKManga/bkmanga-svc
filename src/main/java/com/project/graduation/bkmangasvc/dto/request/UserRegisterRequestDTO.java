package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
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

    @NotNull
    private Integer genderId;
}
