package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.LikeManga;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeMangaRepository extends JpaRepository<LikeManga, Long> {
    Optional<LikeManga> findByMangaAndUser(Manga manga, User user);

    Integer countByMangaAndUser(Manga manga, User user);
}
