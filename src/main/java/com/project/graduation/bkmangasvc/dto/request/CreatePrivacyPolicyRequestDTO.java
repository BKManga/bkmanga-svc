package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreatePrivacyPolicyRequestDTO {

    @NotNull
    @NotBlank
    private String question;

    @NotNull
    @NotBlank
    private String answer;
}
