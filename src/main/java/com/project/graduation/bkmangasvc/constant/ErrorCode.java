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
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "Lỗi xác thực"),
    GENRE_NOT_EXIST("GENRE_NOT_EXIST", "Thể loại truyện không tồn tại"),
    RECORD_NOT_EXIST("RECORD_NOT_EXIST", "Bản ghi không tồn tại"),
    MANGA_NOT_EXIST("MANGA_NOT_EXIST", "Truyện không tồn tại"),
    CHAPTER_NOT_EXIST("CHAPTER_NOT_EXIST", "Chương truyện không tồn tại"),
    CHAPTER_COMMENT_NOT_EXIST("CHAPTER_COMMENT_NOT_EXIST", "Comment này không tồn tại"),
    MANGA_COMMENT_NOT_EXIST("MANGA_COMMENT_NOT_EXIST", "Comment này không tồn tại"),
    AUTHOR_NOT_EXIST("AUTHOR_NOT_EXIST", "Tác giả không tồn tại");

    private final String code;

    private final String message;
}
