package com.exam.examination.dto.response;

import com.exam.examination.enums.AttemptStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class AssessmentAttemptResponse {

    private Long attemptId;

    private Long assessmentId;

    private UUID userId;

    private AttemptStatus status;

    private OffsetDateTime startedAt;

    private OffsetDateTime submittedAt;

    private BigDecimal totalScore;
}
