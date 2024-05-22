package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository<Gender, Integer> {
}
