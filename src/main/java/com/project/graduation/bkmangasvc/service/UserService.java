package com.project.graduation.bkmangasvc.service;

import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.exception.CustomException;

public interface UserService {
    User findByUsername(String username) throws CustomException;
}
