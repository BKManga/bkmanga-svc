package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.MangaStatusEnum;
import com.project.graduation.bkmangasvc.constant.SortingOrderBy;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.*;
import com.project.graduation.bkmangasvc.dto.response.CreateMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaTopResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.helper.TokenHelper;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.MangaService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class MangaServiceImpl implements MangaService {
    private final MangaRepository mangaRepository;
    private final GenreMangaRepository genreMangaRepository;
    private final GenreRepository genreRepository;
    private final MangaStatusRepository mangaStatusRepository;
    private final AuthorRepository authorRepository;
    private final ModelMapper modelMapper;
    private final ViewMangaRepository viewMangaRepository;
    private final MangaAuthorRepository mangaAuthorRepository;
    private final AgeRangeRepository ageRangeRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;

    @Override
    public ApiResponse<List<GetMangaTopResponseDTO>> getListTopManga() {
        Pageable pageable = PageRequest.of(0 , 5);

        List<ViewManga> viewMangaList = viewMangaRepository.findByOrderByNumberOfViewsDesc(pageable);

        List<GetMangaTopResponseDTO> mangaTopResponseDTOList = viewMangaList
                .stream()
                .map(viewManga -> getMangaTopResponseDTO(viewManga.getManga()))
                .toList();

        return ApiResponse.successWithResult(mangaTopResponseDTOList);
    }

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
    public ApiResponse<Page<GetMangaResponseDTO>> getMangaList(GetMangaRequestDTO getMangaRequestDTO) {
        Pageable pageable = PageRequest.of(
                getMangaRequestDTO.getPage(),
                getMangaRequestDTO.getSize()
        );

        Page<Manga> mangaPage = mangaRepository.findAll(pageable);

        Page<GetMangaResponseDTO> mangaResponseDTOPage = mangaPage.map(this::getMangaResponseDTO);

        return ApiResponse.successWithResult(mangaResponseDTOPage);
    }

    @Override
    public ApiResponse<GetMangaResponseDTO> getMangaDetail(
            GetMangaDetailRequestDTO getMangaDetailRequestDTO
    ) throws CustomException {

        Manga foundManga = getMangaValue(getMangaDetailRequestDTO.getMangaId());

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

    @Override
    public ApiResponse<CreateMangaResponseDTO> createManga(CreateMangaRequestDTO createMangaRequestDTO) throws CustomException {
        List<Author> authorList = authorRepository.findByIdIn(createMangaRequestDTO.getListAuthorId());
        List<Genre> genreList = genreRepository.findByIdIn(createMangaRequestDTO.getListGenreId());
        User userUpdate = getUserValue(TokenHelper.getPrincipal());

        Manga manga = new Manga();

        MangaStatus mangaStatus = getMangaStatus(MangaStatusEnum.IN_PROCESS.getStatus());
        AgeRange ageRange = getAgeRangeValue(createMangaRequestDTO.getAgeRangeId());

        manga.setName(createMangaRequestDTO.getName());
        manga.setOtherName(createMangaRequestDTO.getOtherName());
        manga.setDescription(createMangaRequestDTO.getDescription());
        manga.setMangaStatus(mangaStatus);
        manga.setAgeRange(ageRange);
        manga.setUpdatedBy(userUpdate);
        manga.setLastChapterUploadAt(new Date());

        mangaRepository.save(manga);

        List<MangaAuthor> mangaAuthorList = new ArrayList<>();
        List<GenreManga> mangaGenreList = new ArrayList<>();

        for (Author author : authorList) {
            mangaAuthorList.add(new MangaAuthor(author, manga));
        }

        for (Genre genre : genreList) {
            mangaGenreList.add(new GenreManga(manga, genre));
        }

        mangaAuthorRepository.saveAll(mangaAuthorList);
        genreMangaRepository.saveAll(mangaGenreList);

        Chapter chapter = new Chapter();
        chapter.setManga(manga);
        chapter.setUploadedBy(userUpdate);
        chapter.setName(createMangaRequestDTO.getFirstChapterName());

        chapterRepository.save(chapter);

        ViewManga viewManga = new ViewManga(0L, manga);

        viewMangaRepository.save(viewManga);

        CreateMangaResponseDTO createMangaResponseDTO = new CreateMangaResponseDTO(
                manga,
                chapter
        );

        return ApiResponse.successWithResult(createMangaResponseDTO);
    }

    @Override
    public ApiResponse<Manga> updateManga(UpdateMangaRequestDTO updateMangaRequestDTO) throws CustomException {

        Manga manga = getMangaValue(updateMangaRequestDTO.getMangaId());
        User userUpdate = getUserValue(TokenHelper.getPrincipal());
        MangaStatus mangaStatus = getMangaStatus(updateMangaRequestDTO.getMangaStatusId());
        AgeRange ageRange = getAgeRangeValue(updateMangaRequestDTO.getAgeRangeId());

        List<Integer> listGenreIdOrigin = manga.getGenreMangaList().stream().map(
                element -> element.getGenre().getId())
                .toList();

        List<Integer> listAuthorIdOrigin = manga.getMangaAuthorList().stream().map(
                element -> element.getAuthor().getId()
        ).toList();

        HashSet<Integer> hashSetGenreOrigin = new HashSet<>(listGenreIdOrigin);
        HashSet<Integer> hashSetGenre = new HashSet<>(updateMangaRequestDTO.getListAuthorId());
        HashSet<Integer> hashSetAuthorOrigin = new HashSet<>(listAuthorIdOrigin);
        HashSet<Integer> hashSetAuthor = new HashSet<>(updateMangaRequestDTO.getListAuthorId());

        if (!hashSetGenreOrigin.equals(hashSetGenre)) {
            genreMangaRepository.deleteAllByManga(manga);

            List<GenreManga> newGenreList = new ArrayList<>();

            List<Genre> genreList = genreRepository.findByIdIn(updateMangaRequestDTO.getListGenreId());

            for (Genre genre : genreList) {
                newGenreList.add(new GenreManga(manga, genre));
            }

            genreMangaRepository.saveAll(newGenreList);
        }

        if (!hashSetAuthorOrigin.equals(hashSetAuthor)) {
            mangaAuthorRepository.deleteAllByManga(manga);

            List<MangaAuthor> newAuthorList = new ArrayList<>();

            List<Author> authorList = authorRepository.findByIdIn(updateMangaRequestDTO.getListAuthorId());

            for (Author author : authorList) {
                newAuthorList.add(new MangaAuthor(author, manga));
            }

            mangaAuthorRepository.saveAll(newAuthorList);
        }

        manga.setName(updateMangaRequestDTO.getName());
        manga.setOtherName(updateMangaRequestDTO.getOtherName());
        manga.setDescription(updateMangaRequestDTO.getDescription());
        manga.setMangaStatus(mangaStatus);
        manga.setAgeRange(ageRange);
        manga.setUpdatedBy(userUpdate);

        mangaRepository.save(manga);

        return ApiResponse.successWithResult(manga);
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

    private GetMangaTopResponseDTO getMangaTopResponseDTO (Manga manga) {

        GetMangaTopResponseDTO getMangaTopResponseDTO = modelMapper.map(manga, GetMangaTopResponseDTO.class);
        getMangaTopResponseDTO.setNumberOfFollow(manga.getFollowList().size());
        getMangaTopResponseDTO.setNumberOfLikes(manga.getLikeMangaList().size());

        Optional<Chapter> lastChapter = manga.getChapterList().stream().findFirst();

        lastChapter.ifPresent(getMangaTopResponseDTO::setLastChapter);

        return getMangaTopResponseDTO;
    }

    private GetMangaResponseDTO getMangaResponseDTO (Manga manga) {
        GetMangaResponseDTO getMangaResponseDTO = modelMapper.map(manga, GetMangaResponseDTO.class);
        getMangaResponseDTO.setNumberOfFollow(manga.getFollowList().size());
        getMangaResponseDTO.setNumberOfLikes(manga.getLikeMangaList().size());

        return getMangaResponseDTO;
    }

    private AgeRange getAgeRangeValue(Integer ageRangeId) throws CustomException {
        Optional<AgeRange> foundAgeRange = ageRangeRepository.findById(ageRangeId);

        if (foundAgeRange.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return foundAgeRange.get();
    }

    private User getUserValue(Long userId) throws CustomException{

        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }
}
