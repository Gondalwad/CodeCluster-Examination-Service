package com.exam.examination.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssessmentQuestionRequest {

    @NotNull
    private UUID assessmentId;

    @NotNull
    private UUID questionId;

    @NotNull
    @Min(1)
    private Short displayOrder;

    @NotNull
    @Min(1)
    private Integer marks;
}