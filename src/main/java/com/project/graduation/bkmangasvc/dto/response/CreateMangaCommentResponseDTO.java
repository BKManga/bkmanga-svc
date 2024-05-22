package com.project.graduation.bkmangasvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class CreateMangaCommentResponseDTO {

    private Long id;

    private String content;

    private Date createdAt;
}
