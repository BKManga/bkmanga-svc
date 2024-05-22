package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.History;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUserOrderByUpdatedAtDesc(User user);
    Optional<History> findByMangaAndUser(Long mangaId, User user);
}
