package com.project.graduation.bkmangasvc.repository;

import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.entity.MangaStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Long> {
//    Page<Manga> findAllByGenre(Genre genre, Pageable pageable);
    List<Manga> findByIdIn(Collection<Long> mangaIdList);

    Page<Manga> findByOrderByLastChapterUploadAtDesc(Pageable pageable);

    Page<Manga> findMangaByNameContainingOrOtherNameContainingOrderByNameAsc(
            String mangaName,
            String otherName,
            Pageable pageable
    );

    Page<Manga> findMangaByIdInOrderByNameAsc(Collection<Long> mangaIdList, Pageable pageable);

    Page<Manga> findMangaByIdNotInAndMangaStatus(
            Collection<Long> mangaIdDenyList,
            MangaStatus mangaStatus,
            Pageable pageable
    );

    Page<Manga> findMangaByIdInAndMangaStatus(
            Collection<Long> mangaIdApproveList,
            MangaStatus mangaStatus,
            Pageable pageable
    );

    Page<Manga> findMangaByIdInAndIdNotInAndMangaStatus(
            Collection<Long> mangaIdApproveList,
            Collection<Long> mangaIdDenyList,
            MangaStatus mangaStatus,
            Pageable pageable
    );

    Page<Manga> findMangaByMangaStatus(
            MangaStatus mangaStatus,
            Pageable pageable
    );
}
