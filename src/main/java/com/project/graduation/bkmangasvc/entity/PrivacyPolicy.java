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
public class PrivacyPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, length = 512)
    private String answer;

    @Column(nullable = false)
    private Long updatedBy;

    @UpdateTimestamp
    @Column(
            insertable=false,
            updatable=false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private Date updatedAt;

    public PrivacyPolicy(String question, String answer, Long updatedBy) {
        this.question = question;
        this.answer = answer;
        this.updatedBy = updatedBy;
    }
}
