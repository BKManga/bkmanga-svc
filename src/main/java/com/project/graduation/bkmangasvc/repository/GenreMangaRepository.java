package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.GenreManga;
import com.project.graduation.bkmangasvc.entity.Manga;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenreMangaRepository extends JpaRepository<GenreManga, Long> {
    Page<GenreManga> findGenreMangaByGenre(Genre genre, Pageable pageable);

    List<GenreManga> findGenreMangaByGenre(Genre genre);

    List<GenreManga> findGenreMangaByGenreIn(List<Genre> genreApproveList);
}
