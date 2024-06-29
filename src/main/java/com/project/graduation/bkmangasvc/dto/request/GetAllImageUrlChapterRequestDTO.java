package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetAllImageUrlChapterRequestDTO {

    @NotNull
    private Long mangaId;

    @NotNull
    private Long chapterId;
}
