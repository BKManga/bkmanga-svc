package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.GetListMangaByGenreRequestDTO;
import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.GenreManga;
import com.project.graduation.bkmangasvc.entity.Manga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.GenreMangaRepository;
import com.project.graduation.bkmangasvc.repository.GenreRepository;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {

    private GenreRepository genreRepository;
    private GenreMangaRepository genreMangaRepository;
    private MangaRepository mangaRepository;

    @Override
    public ApiResponse<List<Genre>> getAllGenre() {
        List<Genre> genreList = genreRepository.findAll();
        return ApiResponse.successWithResult(genreList);
    }

    @Override
    public ApiResponse<Genre> getGenreById(Integer id) throws CustomException {
        Genre foundGenre = getValueGenre(id);

        return ApiResponse.successWithResult(foundGenre);
    }

    @Override
    public ApiResponse<List<Manga>> getMangaListByGenreId(GetListMangaByGenreRequestDTO mangaListByGenreRequestDTO)
            throws CustomException {
        Genre foundGenre = getValueGenre(mangaListByGenreRequestDTO.getIdGenre());

        Sort sortData = getSorting("manga_id", mangaListByGenreRequestDTO.getOrderBy());

        Pageable pageable = PageRequest.of(
                mangaListByGenreRequestDTO.getPage(),
                mangaListByGenreRequestDTO.getSize(),
                sortData
        );

        Page<GenreManga> genreMangaPage = genreMangaRepository.findGenreMangaByGenre(foundGenre, pageable);

        List<Manga> listManga = genreMangaPage.get().map(GenreManga::getManga).collect(Collectors.toList());

        return ApiResponse.successWithResult(listManga);
    }

    private Genre getValueGenre(Integer id) throws CustomException {
        Optional<Genre> foundGenre = genreRepository.findById(id);
        if (foundGenre.isEmpty()) {
            throw new CustomException(ErrorCode.GENRE_NOT_EXIST);
        }

        return foundGenre.get();
    }

    private Sort getSorting(String sortField, String sortType) {
        Sort.by(sortField);
        return Sort.by(sortType);
    }
}
