package com.project.graduation.bkmangasvc.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.graduation.bkmangasvc.entity.Level;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetUserManagementResponseDTO {

    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String role;

    private String dateOfBirth;

    private String phoneNumber;

    private Date createdAt;

    private Date updatedAt;

    private UserStatus userStatus;

    private Level level;
}
