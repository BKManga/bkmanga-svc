package com.project.graduation.bkmangasvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class GenreManga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public GenreManga(Manga manga, Genre genre) {
        this.manga = manga;
        this.genre = genre;
    }
}
