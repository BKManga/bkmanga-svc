package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findByIdIn(Collection<Long> chapterIdList);
}
