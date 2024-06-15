package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.PointIncrease;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetChapterDetailRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.UpdateChapterRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetChapterDetailResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.ChapterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChapterServiceImpl implements ChapterService {
    private final MangaRepository mangaRepository;
    private final ChapterRepository chapterRepository;
    private final ViewMangaRepository viewMangaRepository;
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public ApiResponse<List<Chapter>> getChapterByManga(
            GetListChapterRequestDTO getListChapterRequestDTO
    ) throws CustomException {
        Manga manga = getMangaValue(getListChapterRequestDTO.getMangaId());

        List<Chapter> chapterList = manga.getChapterList();

        return ApiResponse.successWithResult(chapterList);
    }

    @Override
    public ApiResponse<GetChapterDetailResponseDTO> getChapterDetail(
            GetChapterDetailRequestDTO getChapterDetailRequestDTO
    ) throws CustomException {
        Manga manga = getMangaValue(getChapterDetailRequestDTO.getMangaId());
        Chapter chapter = getChapterValue(getChapterDetailRequestDTO.getChapterId());
        User user = getUserValue(getChapterDetailRequestDTO.getUserId());

        GetChapterDetailResponseDTO getChapterDetailResponseDTO = new GetChapterDetailResponseDTO();

        List<Chapter> listChapter = manga.getChapterList();

        List<Long> listChapterId = listChapter.stream().map(Chapter::getId).toList();

        int indexOfChapter = listChapterId.indexOf(chapter.getId());

        if (indexOfChapter != -1) {
            if (indexOfChapter != 0) {
                getChapterDetailResponseDTO.setNextChapterId(listChapterId.get(indexOfChapter - 1));
            }

            if (indexOfChapter < listChapterId.size() - 1) {
                getChapterDetailResponseDTO.setPreviousChapterId(listChapterId.get(indexOfChapter + 1));
            }
        }

        getChapterDetailResponseDTO.setManga(manga);
        getChapterDetailResponseDTO.setChapter(chapter);

        Level level = user.getLevel();

        level.setPoint(level.getPoint() + PointIncrease.INCREASE.getPointValue());

        ViewManga viewManga = manga.getViewManga();
        viewManga.setNumberOfViews(viewManga.getNumberOfViews() + 1);
        viewMangaRepository.save(viewManga);

        return ApiResponse.successWithResult(getChapterDetailResponseDTO);
    }

    @Override
    public ApiResponse<Chapter> createChapter(CreateChapterRequestDTO createChapterRequestDTO) throws CustomException {

        Manga manga = getMangaValue(createChapterRequestDTO.getMangaId());
        User userUpdate = getUserValue(createChapterRequestDTO.getUploadedById());

        Chapter chapter = new Chapter();
        chapter.setName(createChapterRequestDTO.getName());
        chapter.setManga(manga);
        chapter.setUploadedBy(userUpdate);

        chapterRepository.save(chapter);

        manga.setLastChapterUploadAt(new Date());
        mangaRepository.save(manga);

        return ApiResponse.successWithResult(chapter);
    }

    @Override
    public ApiResponse<Chapter> updateChapter(UpdateChapterRequestDTO updateChapterRequestDTO) throws CustomException {
        Chapter chapter = getChapterValue(updateChapterRequestDTO.getChapterId());
        chapter.setName(updateChapterRequestDTO.getName());
        chapterRepository.save(chapter);

        return ApiResponse.successWithResult(chapter);
    }

    private Manga getMangaValue(Long mangaId) throws CustomException {
        Optional<Manga> foundManga = mangaRepository.findById(mangaId);

        if (foundManga.isEmpty()) {
            throw new CustomException(ErrorCode.MANGA_NOT_EXIST);
        }

        return foundManga.get();
    }

    private Chapter getChapterValue(Long chapterId) throws CustomException {
        Optional<Chapter> foundChapter = chapterRepository.findById(chapterId);

        if (foundChapter.isEmpty()) {
            throw new CustomException(ErrorCode.CHAPTER_NOT_EXIST);
        }

        return foundChapter.get();
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
