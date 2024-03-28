package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.MangaComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaCommentRepository extends JpaRepository<MangaComment, Long> {
}
