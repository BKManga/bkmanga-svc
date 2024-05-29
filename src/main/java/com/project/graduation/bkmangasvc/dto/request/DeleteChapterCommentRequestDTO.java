package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteChapterCommentRequestDTO {

    @NotNull
    private Long chapterCommentId;

    @NotNull
    private Long userId;
}
