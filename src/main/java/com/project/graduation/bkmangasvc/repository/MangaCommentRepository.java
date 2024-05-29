package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.MangaComment;
import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MangaCommentRepository extends JpaRepository<MangaComment, Long> {
    Page<MangaComment> findMangaCommentByManga(Manga manga, Pageable pageable);

    Optional<MangaComment> findMangaCommentByIdAndUser(Long id, User user);
}