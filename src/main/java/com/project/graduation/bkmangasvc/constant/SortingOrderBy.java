package com.project.graduation.bkmangasvc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SortingOrderBy {
    ASC("ASC"),
    DESC("DESC");

    private final String orderBy;
}
