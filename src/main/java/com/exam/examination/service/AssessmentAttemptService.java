package com.exam.examination.service;
import com.exam.examination.dto.response.AssessmentAttemptResponse;

import java.util.UUID;

public interface AssessmentAttemptService {
    AssessmentAttemptResponse startAttempt(
            UUID userId,
            Long assessmentId
    );

    AssessmentAttemptResponse fetchAttempt(
            Long attemptId
    );
}
