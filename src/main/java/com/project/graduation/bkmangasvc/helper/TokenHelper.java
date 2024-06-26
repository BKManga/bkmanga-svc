package com.project.graduation.bkmangasvc.helper;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserRole;
import com.project.graduation.bkmangasvc.exception.CustomException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class TokenHelper {

    public static Long getPrincipal () throws CustomException {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (Long) authentication.getPrincipal();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.AUTHORIZATION_ERROR);
        }
    }

    public static boolean checkAuthentication () {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return authentication.getAuthorities().contains(UserRole.USER.getCode());
        } catch (Exception e) {
            return false;
        }
    }
}
