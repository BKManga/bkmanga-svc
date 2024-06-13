package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.OutLawReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutLawReportRepository extends JpaRepository<OutLawReport, Long> {
    Page<OutLawReport> findByOrderByCreatedAtDesc(Pageable pageable);
}
