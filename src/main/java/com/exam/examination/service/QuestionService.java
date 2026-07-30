package com.exam.examination.service;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;

import java.util.List;

public interface QuestionService {

    QuestionResponse createQuestion(CreateQuestionRequest request);

    List<QuestionResponse> getQuestions(
            Difficulty difficulty,
            QuestionType type
    );

    QuestionResponse getFullQuestion(
            Long questionId
    );



}