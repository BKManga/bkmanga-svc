package com.project.graduation.bkmangasvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateMangaCommentResponseDTO {

    private Long id;

    private String content;
}
