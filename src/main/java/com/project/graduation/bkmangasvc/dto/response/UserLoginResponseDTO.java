package com.project.graduation.bkmangasvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponseDTO {
    private String tokenBearer;
}
