package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.User;
import com.project.graduation.bkmangasvc.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsUserByEmail(String email);

    Optional<User> findByIdAndUserStatus(Long id, UserStatus userStatus);
}
