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
public class OutLawProcessStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    public OutLawProcessStatus(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "outLawProcessStatus")
    private List<OutLawReport> outLawReportList = new ArrayList<>();
}
