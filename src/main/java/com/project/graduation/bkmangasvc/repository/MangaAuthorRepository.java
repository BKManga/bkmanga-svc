package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.MangaAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaAuthorRepository extends JpaRepository<MangaAuthor, Long> {
    void deleteAllByManga(Manga manga);
}
