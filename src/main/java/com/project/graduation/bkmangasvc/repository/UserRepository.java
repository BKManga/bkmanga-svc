package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsUserByEmail(String email);
}
