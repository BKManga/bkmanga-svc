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
public class ViewManga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long numberOfViews;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "manga_id")
    private Manga manga;

    public ViewManga(Long numberOfViews, Manga manga) {
        this.numberOfViews = numberOfViews;
        this.manga = manga;
    }
}
