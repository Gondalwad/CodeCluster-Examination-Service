package com.exam.examination.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mcq_options")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCQOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "option_text", nullable = false, columnDefinition = "TEXT")
    private String optionText;

    @Column(name = "is_correct", nullable = false)
    private Boolean isCorrect;

    @Column(name = "display_order", nullable = false)
    private Short displayOrder;
}