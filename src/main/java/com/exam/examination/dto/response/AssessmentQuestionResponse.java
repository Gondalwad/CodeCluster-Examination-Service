package com.exam.examination.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentQuestionResponse {

    private UUID assessmentId;

    private UUID questionId;

    private Short displayOrder;

    private Integer marks;
}