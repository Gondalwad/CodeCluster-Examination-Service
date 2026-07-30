package com.exam.examination.repository;

import com.exam.examination.entity.AssessmentQuestion;
import com.exam.examination.entity.AssessmentQuestionId;
import com.exam.examination.entity.Question;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssessmentQuestionRepository
        extends JpaRepository<AssessmentQuestion, AssessmentQuestionId> {

    List<AssessmentQuestion> findByIdAssessmentId(Long assessmentId);

    boolean existsByIdAssessmentIdAndIdQuestionId(
            Long assessmentId,
            Long questionId
    );
}
