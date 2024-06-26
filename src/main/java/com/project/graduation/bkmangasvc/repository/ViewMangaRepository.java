package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.ViewManga;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViewMangaRepository extends JpaRepository<ViewManga, Long> {
    List<ViewManga> findByOrderByNumberOfViewsDesc(Pageable pageable);
}
