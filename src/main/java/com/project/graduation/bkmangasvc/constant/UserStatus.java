package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserStatus {
    ACTIVE(1, "active"),
    BANNED(2, "banned"),
    DISABLED(3, "disabled");

    private final Integer code;

    private final String codeName;
}
