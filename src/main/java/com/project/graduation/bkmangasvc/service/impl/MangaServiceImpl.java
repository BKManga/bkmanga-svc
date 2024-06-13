package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.SortingOrderBy;
import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.MangaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;
    private final GenreMangaRepository genreMangaRepository;
    private final GenreRepository genreRepository;
    private final MangaStatusRepository mangaStatusRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<Page<GetMangaResponseDTO>> getMangaListByLastUploadChapter(
            GetMangaListByUploadChapterRequestDTO getMangaListByUploadChapterRequestDTO
    ) {

        Pageable pageable = PageRequest.of(
                getMangaListByUploadChapterRequestDTO.getPage(),
                getMangaListByUploadChapterRequestDTO.getSize()
        );

        Page<Manga> mangaPage = mangaRepository.findByOrderByLastChapterUploadAtDesc(pageable);

        Page<GetMangaResponseDTO> mangaResponseDTOPage = mangaPage.map(this::getMangaResponseDTO);

        return ApiResponse.successWithResult(mangaResponseDTOPage);
    }

    @Override
    public ApiResponse<GetMangaResponseDTO> getMangaDetail(
            GetMangaRequestDTO getMangaRequestDTO
    ) throws CustomException {

        Manga foundManga = getMangaValue(getMangaRequestDTO.getMangaId());

        return ApiResponse.successWithResult(getMangaResponseDTO(foundManga));
    }

    @Override
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByName(GetMangaByNameRequestDTO getMangaByNameRequestDTO) {

        Pageable pageable = PageRequest.of(
                getMangaByNameRequestDTO.getPage(),
                getMangaByNameRequestDTO.getSize(),
                getSorting("createdAt", getMangaByNameRequestDTO.getOrderBy())
        );

        Page<Manga> mangaPage = mangaRepository.findMangaByNameContainingOrOtherNameContainingOrderByNameAsc(
                getMangaByNameRequestDTO.getName(),
                getMangaByNameRequestDTO.getOtherName(),
                pageable
        );

        Page<GetMangaResponseDTO> getMangaResponseDTOPage = getPageMangaResponseDTO(mangaPage);

        return ApiResponse.successWithResult(getMangaResponseDTOPage);
    }

    @Override
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByGenre(
            GetMangaByGenreRequestDTO getMangaByGenreRequestDTO
    ) throws CustomException {
        Genre genre = getGenreValue(getMangaByGenreRequestDTO.getGenreId());

        List<Long> mangaIdList = genreMangaRepository.findGenreMangaByGenre(genre)
                .stream()
                .map(genreManga -> genreManga.getManga().getId()).toList();

        Pageable pageable = PageRequest.of(
                getMangaByGenreRequestDTO.getPage(),
                getMangaByGenreRequestDTO.getSize(),
                getSorting("createdAt", getMangaByGenreRequestDTO.getOrderBy())
        );

        Page<Manga> mangaPage = mangaRepository.findMangaByIdInOrderByNameAsc(mangaIdList, pageable);

        Page<GetMangaResponseDTO> getMangaResponseDTOPage = getPageMangaResponseDTO(mangaPage);

        return ApiResponse.successWithResult(getMangaResponseDTOPage);
    }

    @Override
    public ApiResponse<List<GetMangaResponseDTO>> searchMangaByAuthor(
            GetMangaByAuthorDTO getMangaByAuthorDTO
    ) throws CustomException {
        Author author = getAuthorValue(getMangaByAuthorDTO.getAuthorId());

        List<GetMangaResponseDTO> mangaList = author.getMangaAuthorList()
                .stream()
                .map(mangaAuthor -> getMangaResponseDTO(mangaAuthor.getManga()))
                .toList();

        return ApiResponse.successWithResult(mangaList);
    }

    @Override
    public ApiResponse<Page<GetMangaResponseDTO>> searchMangaByFilter(
            GetMangaByFilterRequestDTO getMangaByFilterRequestDTO
    ) throws CustomException {

        List<Long> mangaIdApproveList = new ArrayList<>();
        List<Long> mangaIdDenyList = new ArrayList<>();

        MangaStatus mangaStatus = getMangaStatus(getMangaByFilterRequestDTO.getMangaStatus());

        if (!getMangaByFilterRequestDTO.getGenreDenyList().isEmpty()) {
            List<Genre> genreDenyList = genreRepository.findByIdIn(getMangaByFilterRequestDTO.getGenreDenyList());

            mangaIdDenyList = genreMangaRepository.findGenreMangaByGenreIn(genreDenyList)
                    .stream()
                    .map(genreManga -> genreManga.getManga().getId())
                    .distinct()
                    .toList();
        }

        if (!getMangaByFilterRequestDTO.getGenreApproveList().isEmpty()) {
            List<Genre> genreApproveList = genreRepository.findByIdIn(getMangaByFilterRequestDTO.getGenreApproveList());

            mangaIdApproveList = genreMangaRepository.findGenreMangaByGenreIn(genreApproveList)
                    .stream()
                    .map(genreManga -> genreManga.getManga().getId())
                    .distinct()
                    .toList();
        }

        Pageable pageable = PageRequest.of(
                getMangaByFilterRequestDTO.getPage(),
                getMangaByFilterRequestDTO.getSize(),
                getSorting("createdAt", getMangaByFilterRequestDTO.getOrderBy())
        );

        Page<Manga> mangaPage = getMagaPageValue(
                mangaIdApproveList,
                mangaIdDenyList,
                mangaStatus,
                pageable
        );

        Page<GetMangaResponseDTO> getMangaResponseDTOPage = getPageMangaResponseDTO(mangaPage);

        return ApiResponse.successWithResult(getMangaResponseDTOPage);
    }

    private Genre getGenreValue(Integer genreId) throws CustomException {
        Optional<Genre> foundGenre = genreRepository.findById(genreId);

        if (foundGenre.isEmpty()) {
            throw new CustomException(ErrorCode.GENRE_NOT_EXIST);
        }

        return foundGenre.get();
    }

    private Author getAuthorValue(Integer authorId) throws CustomException {
        Optional<Author> foundAuthor = authorRepository.findById(authorId);

        if (foundAuthor.isEmpty()) {
            throw new CustomException(ErrorCode.AUTHOR_NOT_EXIST);
        }

        return foundAuthor.get();
    }

    private Page<Manga> getMagaPageValue(
            List<Long> mangaIdApproveList,
            List<Long> mangaIdDenyList,
            MangaStatus mangaStatus,
            Pageable pageable
    ) {

        Page<Manga> mangaPage = Page.empty();

        if (mangaIdApproveList.isEmpty() && !mangaIdDenyList.isEmpty()) {
            mangaPage = mangaRepository.findMangaByIdNotInAndMangaStatus(
                    mangaIdDenyList,
                    mangaStatus,
                    pageable
            );
        } else if (!mangaIdApproveList.isEmpty() && mangaIdDenyList.isEmpty()) {
            mangaPage = mangaRepository.findMangaByIdInAndMangaStatus(
                    mangaIdApproveList,
                    mangaStatus,
                    pageable
            );
        } else if (!mangaIdApproveList.isEmpty() && !mangaIdDenyList.isEmpty()) {
            mangaPage = mangaRepository.findMangaByIdInAndIdNotInAndMangaStatus(
                    mangaIdApproveList,
                    mangaIdDenyList,
                    mangaStatus,
                    pageable
            );
        } else {
            mangaPage = mangaRepository.findMangaByMangaStatus(
                    mangaStatus,
                    pageable
            );
        }

        return mangaPage;
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> manga = mangaRepository.findById(mangaId);

        if (manga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return manga.get();
    }

    private MangaStatus getMangaStatus(Integer mangaStatusId) throws CustomException {
        Optional<MangaStatus> foundMangaStatus = mangaStatusRepository.findById(mangaStatusId);

        if (foundMangaStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundMangaStatus.get();
    }

    private Sort getSorting(String sortField, String sortType) {
        if (sortType.equalsIgnoreCase(SortingOrderBy.DESC.getOrderBy())) {
            return Sort.by(sortField).descending();
        }
        return Sort.by(sortField).ascending();
    }

    private Page<GetMangaResponseDTO> getPageMangaResponseDTO (Page<Manga> mangaPage) {
        return mangaPage.map(this::getMangaResponseDTO);
    }

    private GetMangaResponseDTO getMangaResponseDTO (Manga manga) {
        GetMangaResponseDTO getMangaResponseDTO = modelMapper.map(manga, GetMangaResponseDTO.class);
        getMangaResponseDTO.setNumberOfFollow(manga.getFollowList().size());
        getMangaResponseDTO.setNumberOfLikes(manga.getLikeMangaList().size());

        return getMangaResponseDTO;
    }
}
