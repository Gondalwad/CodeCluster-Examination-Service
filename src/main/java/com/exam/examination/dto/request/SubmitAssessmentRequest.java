package com.exam.examination.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitAssessmentRequest {

    private Long assessmentId;

    private UUID userId;

    private List<AnswerRequest> answers;
}