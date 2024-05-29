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
public class ErrorType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public ErrorType(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "errorType", fetch = FetchType.LAZY)
    private List<ChapterReport> chapterReportList = new ArrayList<>();
}