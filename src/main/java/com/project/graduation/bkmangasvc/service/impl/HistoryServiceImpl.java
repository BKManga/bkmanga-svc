package com.project.graduation.bkmangasvc.service.impl;

import com.project.graduation.bkmangasvc.constant.ErrorCode;
import com.project.graduation.bkmangasvc.constant.UserStatusEnum;
import com.project.graduation.bkmangasvc.dto.request.CreateOrEditHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.DeleteHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.request.GetListHistoryRequestDTO;
import com.project.graduation.bkmangasvc.dto.response.GetListHistoryResponseDTO;
import com.project.graduation.bkmangasvc.dto.response.GetMangaResponseDTO;
import com.project.graduation.bkmangasvc.entity.*;
import com.project.graduation.bkmangasvc.exception.CustomException;
import com.project.graduation.bkmangasvc.model.ApiResponse;
import com.project.graduation.bkmangasvc.model.HistoryResponse;
import com.project.graduation.bkmangasvc.repository.*;
import com.project.graduation.bkmangasvc.service.HistoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final MangaRepository mangaRepository;
    private final ChapterRepository chapterRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<GetListHistoryResponseDTO> getAllHistoryByUser(
            GetListHistoryRequestDTO getListHistoryRequestDTO
    ) throws CustomException {
        User user = getUserValue(getListHistoryRequestDTO.getUserId());

        List<History> historyList = historyRepository.findByUserOrderByUpdatedAtDesc(user);

        List<Long> mangaIdList = new ArrayList<>();
        List<Long> chapterIdList = new ArrayList<>();
        mangaIdList.add(0L);
        chapterIdList.add(0L);
        mangaIdList.addAll(historyList.stream().map(History::getManga).toList());
        chapterIdList.addAll(historyList.stream().map(History::getChapter).toList());

        List<Manga> mangaList = mangaRepository.findByIdIn(mangaIdList);
        List<Chapter> chapterList = chapterRepository.findByIdIn(chapterIdList);

        List<HistoryResponse> historyResponseList = getListHistoryResponse(historyList, mangaList, chapterList);

        return ApiResponse.successWithResult(new GetListHistoryResponseDTO(historyResponseList));
    }

    @Override
    public ApiResponse<?> createOrEditHistory(
            CreateOrEditHistoryRequestDTO createOrEditHistoryRequestDTO
    ) throws CustomException {
        User user = getUserValue(createOrEditHistoryRequestDTO.getUserId());

        Optional<History> foundHistory = historyRepository.findByMangaAndUser(
                createOrEditHistoryRequestDTO.getMangaId(),
                user
        );

        if (foundHistory.isPresent()) {
            History historyValue = foundHistory.get();
            historyValue.setChapter(createOrEditHistoryRequestDTO.getChapterId());
            historyRepository.save(historyValue);

            return ApiResponse.successWithResult(null);
        }

        History history = new History();
        history.setManga(createOrEditHistoryRequestDTO.getMangaId());
        history.setUser(user);
        history.setChapter(createOrEditHistoryRequestDTO.getChapterId());
        historyRepository.save(history);

        return ApiResponse.successWithResult(null);
    }

    @Override
    public ApiResponse<?> deleteHistory(
            DeleteHistoryRequestDTO deleteHistoryRequestDTO
    ) throws CustomException {
        History history = getHistoryValue(deleteHistoryRequestDTO.getId());

        historyRepository.delete(history);

        return ApiResponse.successWithResult(null);
    }

    private User getUserValue(Long userId) throws CustomException {
        Optional<UserStatus> userStatus = userStatusRepository.findById(UserStatusEnum.ACTIVE.getCode());

        if (userStatus.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        Optional<User> foundUser = userRepository.findByIdAndUserStatus(userId, userStatus.get());

        if (foundUser.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_EXIST);
        }

        return foundUser.get();
    }

    private History getHistoryValue(Long historyId) throws CustomException {
        Optional<History> foundHistory = historyRepository.findById(historyId);

        if (foundHistory.isEmpty()) {
            throw new CustomException(ErrorCode.RECORD_NOT_EXIST);
        }

        return foundHistory.get();
    }

    private List<HistoryResponse> getListHistoryResponse(
            List<History> historyList,
            List<Manga> mangaList,
            List<Chapter> chapterList
    ) {
        List<HistoryResponse> historyResponseList = new ArrayList<>();

        Map<Long, Manga> mangaMap = mangaList.stream()
                .collect(Collectors.toMap(Manga::getId, manga -> manga));

        Map<Long, Chapter> chapterMap = chapterList.stream()
                .collect(Collectors.toMap(Chapter::getId, chapter -> chapter));

        historyList.forEach(history -> {
            Manga foundManga = mangaMap.get(history.getManga());
            if (foundManga != null) {
                Chapter foundChapter = chapterMap.get(history.getChapter());
                if (foundChapter != null) {
                    GetMangaResponseDTO getMangaResponseDTO = modelMapper.map(foundManga, GetMangaResponseDTO.class);
                    getMangaResponseDTO.setNumberOfLikes(foundManga.getLikeMangaList().size());
                    getMangaResponseDTO.setNumberOfFollow(foundManga.getFollowList().size());

                    historyResponseList.add(new HistoryResponse(
                            history.getId(),
                            getMangaResponseDTO,
                            foundChapter
                    ));
                }
            }
        });

        return historyResponseList;
    }
}
