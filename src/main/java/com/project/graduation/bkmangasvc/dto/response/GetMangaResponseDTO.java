package com.project.graduation.bkmangasvc.dto.response;

import com.project.graduation.bkmangasvc.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class GetMangaResponseDTO {

    private Long id;

    private String name;

    private String otherName;
    
    private String description;

    private User updatedBy;

    private MangaStatus mangaStatus;

    private ViewManga viewManga;

    private List<GenreManga> genreMangaList = new ArrayList<>();

    private List<MangaAuthor> mangaAuthorList = new ArrayList<>();

    private List<Chapter> chapterList = new ArrayList<>();
    
    private Integer numberOfFollow;

    private Integer numberOfLikes;

    private AgeRange ageRange;

    private Date createdAt;
}
