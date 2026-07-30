package com.exam.examination.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateAssessmentQuestionRequest {

    @NotNull
    private Long assessmentId;

    @NotEmpty
    private List<AQStructure> questionIds;
}