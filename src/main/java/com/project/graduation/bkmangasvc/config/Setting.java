package com.project.graduation.bkmangasvc.config;

import lombok.NoArgsConstructor;

public class Setting {
    private Setting() {
    }
    public static final String TOKEN_SECRET = "bkmanga";
    public static final Long TOKEN_EXPIRATION_TIME = 1L;
}
