package com.exam.examination.repository;

import com.exam.examination.entity.MCQOptions;
import com.exam.examination.entity.Question;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionMCQRepository
        extends JpaRepository<MCQOptions, Long> {

    List<MCQOptions> findByQuestionQuestionId(Long questionId);

    boolean existsByQuestionQuestionId(Long questionId);


}