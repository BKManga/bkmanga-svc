package com.project.graduation.bkmangasvc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChapterCommentResponseDTO {

    private Long id;

    private String content;
}
