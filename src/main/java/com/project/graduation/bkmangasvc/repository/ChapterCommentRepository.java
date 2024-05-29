package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.ChapterComment;
import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChapterCommentRepository extends JpaRepository<ChapterComment, Long> {
    Page<ChapterComment> findChapterCommentByChapter(Chapter chapter, Pageable pageable);

    Optional<ChapterComment> findChapterCommentByIdAndUser(Long id, User user);
}
