package com.exam.examination.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitAssessmentRequest {

    private Long assessmentId;

    private Long studentId;

    private List<AnswerRequest> answers;
}