package com.project.graduation.bkmangasvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private T result;

    private String errorCode;

    private Object message;

    private int responseCode;

    public static <T> ApiResponse<T> successWithResult(T result) {
        return new ApiResponse<>(result, null, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> successWithResult(T result, String message) {
        return new ApiResponse<>(result, null, message, HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message) {
        return new ApiResponse<>(null, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message, T result) {
        return new ApiResponse<>(result, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, Object message, T result, HttpStatus httpStatus) {
        return new ApiResponse<>(result, errorCode, message, httpStatus.value());
    }
}
