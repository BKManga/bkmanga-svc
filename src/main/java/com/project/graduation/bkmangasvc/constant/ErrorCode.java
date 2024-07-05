package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi không xác định"),
    USER_NOT_EXIST("USER_NOT_EXIST", "Người dùng không tồn tại"),
    USER_EXISTED("USER_EXISTED", "Người dùng đã tồn tại"),
    USERNAME_EXISTED("USERNAME_EXISTED", "Tên người dùng đã tồn tại"),
    NOT_VALIDATED_DATA("NOT_VALIDATED_DATA", "Dữ liệu không hợp lệ"),
    AUTHORIZATION_ERROR("AUTHORIZATION_ERROR", "Lỗi xác thực"),
    OLD_PASSWORD_NOT_CORRECT("OLD_PASSWORD_NOT_CORRECT", "Mật khẩu cũ không chính xác"),
    GENRE_NOT_EXIST("GENRE_NOT_EXIST", "Thể loại truyện không tồn tại"),
    RECORD_NOT_EXIST("RECORD_NOT_EXIST", "Bản ghi không tồn tại"),
    MANGA_NOT_EXIST("MANGA_NOT_EXIST", "Truyện không tồn tại"),
    CHAPTER_NOT_EXIST("CHAPTER_NOT_EXIST", "Chương truyện không tồn tại"),
    CHAPTER_COMMENT_NOT_EXIST("CHAPTER_COMMENT_NOT_EXIST", "Comment này không tồn tại"),
    MANGA_COMMENT_NOT_EXIST("MANGA_COMMENT_NOT_EXIST", "Comment này không tồn tại"),
    AUTHOR_NOT_EXIST("AUTHOR_NOT_EXIST", "Tác giả không tồn tại"),
    AUTHOR_EXISTED("AUTHOR_EXISTED", "Tác giả đã tồn tại"),
    AUTHOR_NAME_EXISTED("AUTHOR_NAME_EXISTED", "Tên tác giả đã tồn tại"),
    GENRE_EXISTED("GENRE_EXISTED", "Thể loại truyện đã tồn tại"),
    GENRE_NAME_EXISTED("GENRE_NAME_EXISTED", "Tên thể loại truyện đã tồn tại"),
    IMAGE_EXTENSION("IMAGE_EXTENSTION", "Định dạng ảnh không đúng"),
    IMAGE_SIZE("IMAGE_SIZE", "Định dạng ảnh không đúng"),
    IMAGE_ERROR("IMAGE_ERROR", "Lỗi khi lấy ảnh"),
    IMAGE_UPLOAD_ERROR("IMAGE_UPLOAD_ERROR", "Lỗi tải ảnh"),
    IMAGE_CHAPTER_NOT_FOUND("IMAGE_CHAPTER_NOT_FOUND", "Không thể tải được ảnh truyện"),
    CHAPTER_IMAGE_UPLOAD_ERROR("CHAPTER_IMAGE_UPLOAD_ERROR", "Lỗi nạp chương truyện");

    private final String code;

    private final String message;
}
