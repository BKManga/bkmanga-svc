package com.project.graduation.bkmangasvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String otherName;

    @Column(nullable = false, length = 512)
    private String description;

    @Column(nullable = false)
    private Long updatedBy;

    @Column(
            insertable=false,
            updatable=false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date createdAt;

    @UpdateTimestamp
    @Column(
            insertable=false,
            updatable=false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date updatedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manga_status_id")
    private MangaStatus mangaStatus;

    @OneToOne(mappedBy = "manga")
    private ViewManga viewManga;

    @OneToMany(mappedBy = "manga")
    private List<GenreManga> genreMangaList = new ArrayList<>();

    @OneToMany(mappedBy = "manga")
    private List<MangaAuthor> mangaAuthorList = new ArrayList<>();

    @OneToMany(mappedBy = "manga")
    private List<MangaComment> mangaCommentList = new ArrayList<>();

    @OneToMany(mappedBy = "manga")
    private List<Chapter> chapterList = new ArrayList<>();

    @OneToMany(mappedBy = "manga")
    private List<LikeManga> likeMangaList = new ArrayList<>();

    @OneToMany(mappedBy = "manga")
    private List<Follow> followList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "age_range_id")
    private AgeRange ageRange;
}
