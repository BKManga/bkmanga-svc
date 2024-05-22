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
public class MangaStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public MangaStatus(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "mangaStatus")
    private List<Manga> mangaList = new ArrayList<>();
}