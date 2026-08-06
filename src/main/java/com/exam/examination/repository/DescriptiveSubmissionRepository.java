package com.exam.examination.repository;

import com.exam.examination.entity.DescriptiveSubmission;
import com.exam.examination.entity.MCQSubmissionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DescriptiveSubmissionRepository
        extends JpaRepository<DescriptiveSubmission, Long> {
}