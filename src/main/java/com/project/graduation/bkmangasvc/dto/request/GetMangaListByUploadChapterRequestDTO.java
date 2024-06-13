package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaListByUploadChapterRequestDTO {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
