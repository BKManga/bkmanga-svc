package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OutLawTypeEnum {
    HATRED(1, "hatred"),
    SEXUAL(2, "sexual"),
    VIOLENCE(3, "violence"),
    OTHER(4, "other");

    private final Integer code;

    private final String message;
}
