package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {
}
