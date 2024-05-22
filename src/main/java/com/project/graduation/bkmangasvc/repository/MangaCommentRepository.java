package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.MangaComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaCommentRepository extends JpaRepository<MangaComment, Long> {
    Page<MangaComment> findMangaCommentByManga(Manga manga, Pageable pageable);
}