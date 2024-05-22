package com.project.graduation.bkmangasvc.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ChapterReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long manga;

    @Column()
    private String description;

    @Column(nullable = false)
    private Long uploadedBy;

    @Column()
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "error_type_id")
    private ErrorType errorType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "error_report_status_id")
    private ErrorReportStatus errorReportStatus;
}
