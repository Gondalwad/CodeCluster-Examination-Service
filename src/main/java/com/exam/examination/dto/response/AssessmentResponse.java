package com.exam.examination.dto.response;

import com.exam.examination.enums.AssessmentStatus;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentResponse {

    private Long assessmentId;

    private String title;

    private String description;

    private Integer durationMinutes;

    private Integer totalMarks;

    private AssessmentStatus status;

    private OffsetDateTime startTime;

    private OffsetDateTime endTime;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
