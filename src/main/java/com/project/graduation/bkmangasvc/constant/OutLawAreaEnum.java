package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OutLawAreaEnum {
    ACCOUNT(1, "account"),
    COMMENT_MANGA(2, "comment manga"),
    COMMENT_CHAPTER(3, "comment chapter");

    private final Integer code;

    private final String message;
}
