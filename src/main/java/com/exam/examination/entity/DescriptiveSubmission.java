package com.exam.examination.entity;

import com.exam.examination.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "descriptive_submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DescriptiveSubmission {

    @Id
    @Column(name = "submission_id")
    private Long submissionId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @Column(name = "response_text", nullable = false, columnDefinition = "TEXT")
    private String responseText;

    @Column(name = "evaluator_feedback", columnDefinition = "TEXT")
    private String evaluatorFeedback;

    @Column(name = "evaluated_by")
    private UUID evaluatedBy;

    @Column(name = "evaluated_at")
    private OffsetDateTime evaluatedAt;
}