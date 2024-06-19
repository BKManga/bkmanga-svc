package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePrivacyPolicyRequestDTO {

    @NotNull
    private Integer id;

    @NotNull
    @NotBlank
    private String question;

    @NotNull
    @NotBlank
    private String answer;
}
