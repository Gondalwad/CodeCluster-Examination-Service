package com.exam.examination.service.impl;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.entity.Question;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.repository.QuestionRepository;
import com.exam.examination.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                repository.findQuestions(difficulty, type);

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

    @Override
    public QuestionResponse getFullQuestion(Long questionId){
        Question question = repository.getFullQuestion(questionId);

        return QuestionResponse.builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .description(question.getDescription())
                .type(question.getType())
                .difficulty(question.getDifficulty())
                .marks(question.getMarks())
                .isPublic(question.getIsPublic())
                .build();

    }
}