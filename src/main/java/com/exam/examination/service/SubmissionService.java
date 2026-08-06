package com.exam.examination.service;

import com.exam.examination.dto.request.SubmitAssessmentRequest;
import com.exam.examination.dto.response.MessageResponse;

public interface SubmissionService {

    MessageResponse submitAssessment(
            SubmitAssessmentRequest request
    );

}