package com.exam.examination.dto.request;

import com.exam.examination.enums.AssessmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAssessmentStatusRequest {

    @NotNull
    private AssessmentStatus status;

}
