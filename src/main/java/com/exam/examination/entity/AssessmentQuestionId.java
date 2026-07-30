package com.exam.examination.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AssessmentQuestionId implements Serializable {

    @Column(name = "assessment_id")
    private UUID assessmentId;

    @Column(name = "question_id")
    private UUID questionId;
}