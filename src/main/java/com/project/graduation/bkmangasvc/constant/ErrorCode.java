package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi không xác định"),
    USER_NOT_EXIST("USER_NOT_EXIST", "Người dùng không tồn tại"),
    USER_EXISTED("USER_EXISTED", "Người dùng đã tồn tại"),
    NOT_VALIDATED_DATA("NOT_VALIDATED_DATA", "Dữ liệu không hợp lệ"),
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "Lỗi xác thực");

    private final String code;

    private final String message;

}
