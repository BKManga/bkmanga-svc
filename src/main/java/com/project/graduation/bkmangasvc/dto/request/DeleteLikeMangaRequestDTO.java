package com.project.graduation.bkmangasvc.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class DeleteLikeMangaRequestDTO {

    @NotNull
    private Long id;
}
