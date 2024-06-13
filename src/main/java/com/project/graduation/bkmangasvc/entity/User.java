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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private String dateOfBirth;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(
            insertable=false,
            updatable=false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    @JsonIgnore
    private Date createdAt;

    @UpdateTimestamp
    @JsonIgnore
    @Column(
            insertable=false,
            updatable=false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date updatedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "status_id", nullable = false)
    private UserStatus userStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ChapterComment> chapterCommentList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<MangaComment> mangaCommentList;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Level level;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<History> historyList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Follow> followList = new ArrayList<>();

    @OneToMany(mappedBy = "uploadedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OutLawReport> uploadOutLawReportList = new ArrayList<>();

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OutLawReport> updatedOutLawReportList = new ArrayList<>();

    @OneToMany(mappedBy = "userReported", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OutLawReport> reportOutLawReportList = new ArrayList<>();

    @OneToMany(mappedBy = "uploadedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ErrorChapterReport> uploadErrorChapterReportList = new ArrayList<>();

    @OneToMany(mappedBy = "updatedBy", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ErrorChapterReport> updateErrorChapterReportList = new ArrayList<>();
}
