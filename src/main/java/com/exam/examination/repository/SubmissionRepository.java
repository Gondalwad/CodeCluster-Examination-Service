package com.exam.examination.repository;

import com.exam.examination.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository
        extends JpaRepository<Submission, Long> {
}

