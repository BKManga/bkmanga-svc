package com.project.graduation.bkmangasvc.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetListFollowRequestDTO {

    @NotNull
    private Integer page;

    @NotNull
    private Integer size;
}
