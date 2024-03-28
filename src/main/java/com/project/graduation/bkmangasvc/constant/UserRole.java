package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String code;
}
