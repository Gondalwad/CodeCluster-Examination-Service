package com.exam.examination.service.impl;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.entity.Question;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.exception.QuestionNotFoundException;
import com.exam.examination.repository.QuestionRepository;
import com.exam.examination.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository repository;

    @Override
    public QuestionResponse createQuestion(CreateQuestionRequest request) {

        if(repository.existsByQuestionId(request.getQuestionId())){
            throw new RuntimeException("Question already exists");
        }

        Question question = Question.builder()
                .questionId(request.getQuestionId())
                .title(request.getTitle())
                .type(request.getType())
                .difficulty(request.getDifficulty())
                .marks(request.getMarks())
                .isPublic(request.getIsPublic())
                .build();

        Question savedQuestion = repository.save(question);

        return QuestionResponse.builder()
                .questionId(savedQuestion.getQuestionId())
                .title(savedQuestion.getTitle())
                .type(savedQuestion.getType())
                .difficulty(savedQuestion.getDifficulty())
                .marks(savedQuestion.getMarks())
                .isPublic(savedQuestion.getIsPublic())
                .build();
    }

    @Override
    public List<QuestionResponse> getQuestions(
            Difficulty difficulty,
            QuestionType type) {

        List<Question> questions =
                repository.findQuestionsByDifficultyAndQuestionType(difficulty, type);

        return questions.stream()
                .map(question -> QuestionResponse.builder()
                        .questionId(question.getQuestionId())
                        .title(question.getTitle())
                        .type(question.getType())
                        .difficulty(question.getDifficulty())
                        .marks(question.getMarks())
                        .isPublic(question.getIsPublic())
                        .build())
                .toList();
    }

    /// gets question based on question Id using questionRepository
    @Override
    public QuestionResponse getFullQuestion(Long questionId){
        Optional<Question> question = repository.findByQuestionId(questionId);
        if(question.isEmpty()){
            throw new QuestionNotFoundException("Question Not Found");
        }
        return QuestionResponse.builder()
                .questionId(question.get().getQuestionId())
                .title(question.get().getTitle())
                .description(question.get().getDescription())
                .type(question.get().getType())
                .difficulty(question.get().getDifficulty())
                .marks(question.get().getMarks())
                .isPublic(question.get().getIsPublic())
                .build();

    }
}