package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.dto.request.CreateGenreRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteGenreRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateGenreRequestDTO;
import com.project.graduation.bkmangasvc.entity.Genre;
import com.project.graduation.bkmangasvc.entity.GenreManga;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.GenreMangaRepository;
import com.project.graduation.bkmangasvc.repository.GenreRepository;
import com.project.graduation.bkmangasvc.repository.MangaRepository;
import com.project.graduation.bkmangasvc.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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
    public ApiResponse<Genre> createGenre(CreateGenreRequestDTO createGenreRequestDTO) throws CustomException {

        String nameGenre = createGenreRequestDTO.getName().toLowerCase(Locale.ROOT);

        checkGenreValue(nameGenre);

        Genre genre = new Genre(nameGenre, createGenreRequestDTO.getDescription());
        genreRepository.save(genre);

        return ApiResponse.successWithResult(genre);
    }

    @Override
    public ApiResponse<Genre> updateGenre(UpdateGenreRequestDTO updateGenreRequestDTO) throws CustomException {
        String nameGenre = updateGenreRequestDTO.getName().toLowerCase(Locale.ROOT);
        Genre genre = getValueGenre(updateGenreRequestDTO.getGenreId());

        checkGenreValue(genre, nameGenre);

        genre.setName(nameGenre);
        genre.setDescription(updateGenreRequestDTO.getDescription());
        genreRepository.save(genre);

        return ApiResponse.successWithResult(genre);
    }

    @Override
    public ApiResponse<?> deleteGenre(DeleteGenreRequestDTO deleteGenreRequestDTO) throws CustomException {
        Genre genre = getValueGenre(deleteGenreRequestDTO.getGenreId());
        List<GenreManga> genreMangaList = genreMangaRepository.findGenreMangaByGenre(genre);
        genreRepository.delete(genre);
        genreMangaRepository.deleteAll(genreMangaList);
        return ApiResponse.successWithResult(null);
    }

    private void checkGenreValue(String nameGenre) throws CustomException {
        Optional<Genre> genre = genreRepository.findByName(nameGenre);

        if (genre.isPresent()) {
            throw new CustomException(ErrorCode.GENRE_EXISTED);
        }
    }

    private void checkGenreValue(Genre genre, String nameGenre) throws CustomException {
        Optional<Genre> foundGenre = genreRepository.findByName(nameGenre);

        if (foundGenre.isPresent()) {
            if (!Objects.equals(foundGenre.get().getId(), genre.getId())) {
                throw new CustomException(ErrorCode.GENRE_NAME_EXISTED);
            }
        }
    }

    private Genre getValueGenre(Integer id) throws CustomException {
        Optional<Genre> foundGenre = genreRepository.findById(id);
        if (foundGenre.isEmpty()) {
            throw new CustomException(ErrorCode.GENRE_NOT_EXIST);
        }

        return foundGenre.get();
    }
}
