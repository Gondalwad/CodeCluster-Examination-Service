package com.exam.examination.service;

import com.exam.examination.dto.request.CreateAssessmentRequest;
import com.exam.examination.dto.request.UpdateAssessmentStatusRequest;
import com.exam.examination.dto.response.AssessmentResponse;

import java.util.UUID;

public interface AssessmentService {

    AssessmentResponse createAssessment(
            CreateAssessmentRequest request
    );

    AssessmentResponse updateAssessmentStatus(
            Long assessmentId,
            UpdateAssessmentStatusRequest request
    );

}
