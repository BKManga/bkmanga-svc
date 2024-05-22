package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.entity.GenreManga;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.service.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;

    @Override
    public ApiResponse<Manga> getMangaById(Long mangaId) throws CustomException {

        Manga foundManga = getMangaValue(mangaId);

        foundManga.getGenreMangaList().forEach(GenreManga::getGenre);
//        foundManga.getLikeMangaList()

        return ApiResponse.successWithResult(foundManga);
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> manga = mangaRepository.findById(mangaId);

        if (manga.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return manga.get();
    }
}
