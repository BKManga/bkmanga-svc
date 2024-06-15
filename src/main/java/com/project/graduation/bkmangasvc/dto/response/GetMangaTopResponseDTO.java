package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaTopResponseDTO {
    private Long id;

    private String name;

    private String otherName;

    private String description;

    private Long updatedBy;

    private MangaStatus mangaStatus;

    private ViewManga viewManga;

    private List<GenreManga> genreMangaList = new ArrayList<>();

    private List<MangaAuthor> mangaAuthorList = new ArrayList<>();

    private Chapter lastChapter;

    private Integer numberOfFollow;

    private Integer numberOfLikes;
}
