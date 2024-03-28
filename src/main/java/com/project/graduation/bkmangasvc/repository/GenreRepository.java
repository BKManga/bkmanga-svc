package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
