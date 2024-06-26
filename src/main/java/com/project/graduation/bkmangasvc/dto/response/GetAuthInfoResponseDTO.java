package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.entity.Gender;
import com.project.graduation.bkmangasvc.entity.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetAuthInfoResponseDTO {

    private String username;

    private String fullName;

    private String dateOfBirth;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private Level level;
}
