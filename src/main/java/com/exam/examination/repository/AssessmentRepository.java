package com.exam.examination.repository;

import com.exam.examination.entity.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Optional<Assessment> findByAssessmentId(Long assessmentId);

    boolean existsByTitle(String title);


}
