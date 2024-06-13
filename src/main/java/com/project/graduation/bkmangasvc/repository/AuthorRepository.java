package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
