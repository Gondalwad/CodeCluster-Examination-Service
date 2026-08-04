package com.exam.examination.service.impl;


import com.exam.examination.dto.response.AssessmentAttemptResponse;
import com.exam.examination.entity.Assessment;
import com.exam.examination.entity.AssessmentAttempt;
import com.exam.examination.enums.AttemptStatus;
import com.exam.examination.repository.AssessmentAttemptRepository;
import com.exam.examination.repository.AssessmentRepository;
import com.exam.examination.service.AssessmentAttemptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssessmentAttemptServiceImpl implements AssessmentAttemptService {

    private final AssessmentRepository assessmentRepository;
    private final AssessmentAttemptRepository assessmentAttemptRepository;

    @Override
    public AssessmentAttemptResponse startAttempt(
            UUID userId,
            Long assessmentId) {

        Assessment assessment = assessmentRepository.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        OffsetDateTime startTime =
                OffsetDateTime.now(ZoneId.of("Asia/Kolkata"));

        AssessmentAttempt assessmentAttempt = AssessmentAttempt.builder()
                .assessment(assessment)
                .userId(userId)
                .status(AttemptStatus.IN_PROGRESS)
                .startedAt(startTime)
                .build();

        AssessmentAttempt savedAttempt = assessmentAttemptRepository.save(assessmentAttempt);

        return AssessmentAttemptResponse.builder()
                .attemptId(savedAttempt.getAttemptId())
                .assessmentId(savedAttempt.getAssessment().getAssessmentId())
                .status(savedAttempt.getStatus())
                .startedAt(savedAttempt.getStartedAt())
                .submittedAt(savedAttempt.getSubmittedAt())
                .totalScore(savedAttempt.getTotalScore())
                .build();
    }

    @Override
    public AssessmentAttemptResponse fetchAttempt(
            Long attemptId
    ){
        AssessmentAttempt attemptData = assessmentAttemptRepository.findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment Attempt not found with id: " + attemptId));

        return AssessmentAttemptResponse.builder()
                .attemptId(attemptData.getAttemptId())
                .assessmentId(attemptData.getAssessment().getAssessmentId())
                .status(attemptData.getStatus())
                .startedAt(attemptData.getStartedAt())
                .submittedAt(attemptData.getSubmittedAt())
                .totalScore(attemptData.getTotalScore())
                .build();

    }

    @Override
    public AssessmentAttemptResponse submitAttempt(Long attemptId) {

        AssessmentAttempt attempt = assessmentAttemptRepository.findById(attemptId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment Attempt not found with id: " + attemptId));

        if (attempt.getStatus() == AttemptStatus.SUBMITTED) {
            throw new RuntimeException("Assessment Attempt has already been submitted.");
        }

        attempt.setStatus(AttemptStatus.SUBMITTED);
        attempt.setSubmittedAt(OffsetDateTime.now());

        AssessmentAttempt updatedAttempt = assessmentAttemptRepository.save(attempt);

        return AssessmentAttemptResponse.builder()
                .attemptId(updatedAttempt.getAttemptId())
                .status(updatedAttempt.getStatus())
                .build();
    }
}