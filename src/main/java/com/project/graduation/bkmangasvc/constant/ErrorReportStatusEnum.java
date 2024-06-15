package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorReportStatusEnum {
    NEW(1, "new"),
    IN_PROCESS(2, "in process"),
    RESOLVED(3, "resolved"),
    REJECTED(4, "rejected"),;

    private final Integer code;

    private final String message;
}
