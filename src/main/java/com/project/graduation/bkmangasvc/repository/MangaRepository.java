package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Long> {
//    Page<Manga> findAllByGenre(Genre genre, Pageable pageable);
    List<Manga> findByIdIn(Collection<Long> mangaIdList);
    List<Manga> findByNameLikeOrOtherNameLike(String name, String otherName);
}
