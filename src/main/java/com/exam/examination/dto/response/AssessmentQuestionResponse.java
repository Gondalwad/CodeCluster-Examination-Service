package com.exam.examination.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentQuestionResponse {

    private Long assessmentId;

    private Long questionId;

    private Short displayOrder;

    private Integer marks;
}