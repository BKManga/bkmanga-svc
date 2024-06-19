package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetChapterDetailRequestDTO {

    @NotNull
    private Long mangaId;

    @NotNull
    private Long chapterId;
}
