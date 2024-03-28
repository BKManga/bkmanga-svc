package com.project.graduation.bkmangasvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

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
    private Integer manga_status;

    @Column(nullable = false)
    private String otherName;

    @Column(nullable = false)
    private Integer ageRange;

    @Column(nullable = false)
    private Long mangaAuthorID;

    @Column(nullable = false)
    private Long genreMangaID;

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
}
