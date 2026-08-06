package com.exam.examination.repository;

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
public interface QuestionRepository
        extends JpaRepository<Question, Long> {

    Optional<Question> findByQuestionId(Long questionId);

    boolean existsByQuestionId(Long questionId);


    @Query("""
    SELECT q
    FROM Question q
    WHERE (:difficulty IS NULL OR q.difficulty = :difficulty)
      AND (:type IS NULL OR q.type = :type)
    """)
    List<Question> findQuestionsByDifficultyAndQuestionType(
            @Param("difficulty") Difficulty difficulty,
            @Param("type") QuestionType type
    );


}
