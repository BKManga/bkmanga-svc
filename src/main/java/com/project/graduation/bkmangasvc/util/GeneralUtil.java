package com.project.graduation.bkmangasvc.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;

import java.util.stream.Collectors;

public class GeneralUtil {
    private GeneralUtil() {}

    public static String processValidationError(Errors errors) {
        return errors.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }

    public static long getMSFromDays(long days) {
        return days * 24 * 60 * 60 * 1000;
    }
}
