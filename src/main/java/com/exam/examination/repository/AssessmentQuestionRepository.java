package com.exam.examination.repository;

import com.exam.examination.entity.AssessmentQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssessmentQuestionRepository
        extends JpaRepository<AssessmentQuestion, UUID> {
}
