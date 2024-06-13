package com.project.graduation.bkmangasvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class AgeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public AgeRange(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "ageRange", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Manga> mangaList = new ArrayList<>();
}
