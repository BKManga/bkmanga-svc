package com.project.graduation.bkmangasvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 512)
    private String description;

    public Genre(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @OneToMany(mappedBy = "genre")
    private List<GenreManga> genreMangaList = new ArrayList<>();
}