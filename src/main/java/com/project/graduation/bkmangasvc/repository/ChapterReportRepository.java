package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.ErrorChapterReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterReportRepository extends JpaRepository<ErrorChapterReport, Long> {
    Page<ErrorChapterReport> findByOrderByCreatedAtDesc(Pageable pageable);
}
