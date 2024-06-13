package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorTypeEnum {
    TRANSLATION(1, "translation"),
    IMAGE_QUALITY(2, "image quality"),
    DUPLICATE(3, "duplicate"),
    LOAD_IMAGE(4, "load image");

    private final Integer code;

    private final String message;
}
