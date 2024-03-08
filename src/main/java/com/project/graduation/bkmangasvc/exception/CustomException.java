package com.project.graduation.bkmangasvc.exception;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
public class CustomException extends Exception{
    private String message;
    private String errorCode;

    public CustomException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.getMessage(), args));
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
    }
}
