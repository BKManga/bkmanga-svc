package com.project.graduation.bkmangasvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CreateChapterCommentResponseDTO {

    private Long id;

    private String content;

    private Date createdAt;
}
