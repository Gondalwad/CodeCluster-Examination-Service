package com.exam.examination.repository;

import com.exam.examination.entity.AssessmentAttempt;
import com.exam.examination.enums.AttemptStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssessmentAttemptRepository extends JpaRepository<AssessmentAttempt, Long>{

    List<AssessmentAttempt> findByUserId(UUID userId);

    List<AssessmentAttempt> findByAssessmentAssessmentId(Long assessmentId);

    Optional<AssessmentAttempt> findByAttemptIdAndUserId(Long attemptId, UUID userId);

    boolean existsByAssessmentAssessmentIdAndUserIdAndStatus(
            Long assessmentId,
            UUID userId,
            AttemptStatus status
    );
}
