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
public class OutLawReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userReported;

    @Column()
    private Long commentReported;

    @Column()
    private String description;

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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "out_law_status_id")
    private OutLawProcessStatus outLawProcessStatus;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "out_law_type_id")
    private OutLawType outLawType;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "out_law_area_id")
    private OutLawArea outLawArea;
}
