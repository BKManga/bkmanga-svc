package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MangaStatusEnum {
    DONE(1),
    IN_PROCESS(2),
    DROPPED(3);

    private final Integer status;
}
