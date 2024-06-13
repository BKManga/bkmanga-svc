package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Follow;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByMangaAndUser(Manga manga, User user);

    Page<Follow> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
