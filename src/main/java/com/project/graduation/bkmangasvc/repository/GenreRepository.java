package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    List<Genre> findByIdIn(List<Integer> genreIdList);

    Optional<Genre> findByName(String name);
}
