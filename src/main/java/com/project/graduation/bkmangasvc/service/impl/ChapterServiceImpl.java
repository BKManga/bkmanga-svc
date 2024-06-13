package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterRequestDTO;
import com.project.graduation.bkmangasvc.entity.Chapter;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.service.ChapterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final MangaRepository mangaRepository;

    @Override
    public ApiResponse<List<Chapter>> getChapterByManga(
            GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException {
        Manga manga = getMangaValue(getListChapterRequestDTO.getMangaId());

        List<Chapter> chapterList = manga.getChapterList();

        return ApiResponse.successWithResult(chapterList);
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(mangaId);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }
}
