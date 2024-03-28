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
public class OutLawReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userIDReported;

    @Column()
    private Long commentIDReported;

    @Column(nullable = false)
    private Integer outLawType;

    @Column(nullable = false)
    private Integer status;

    @Column()
    private String description;

    @Column(nullable = false)
    private Integer areaID;

    @Column(nullable = false)
    private Long uploadedBy;

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
