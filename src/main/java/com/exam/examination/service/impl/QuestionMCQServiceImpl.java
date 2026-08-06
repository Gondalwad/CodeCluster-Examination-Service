package com.exam.examination.service.impl;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.MCQOptionsResponse;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.entity.MCQOptions;
import com.exam.examination.entity.Question;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.exception.QuestionNotFoundException;
import com.exam.examination.repository.QuestionMCQRepository;
import com.exam.examination.repository.QuestionRepository;
import com.exam.examination.service.QuestionMCQService;
import com.exam.examination.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionMCQServiceImpl implements QuestionMCQService {

    private final QuestionMCQRepository questionMCQRepository;

    @Override
    public MCQOptionsResponse fetchOptions(Long questionId) {

        List<MCQOptions> options =
                questionMCQRepository.findByQuestionQuestionId(questionId);

        if (options.isEmpty()) {
            throw new RuntimeException(
                    "Options not found with question ID " + questionId
            );
        }

        return MCQOptionsResponse.builder()
                .questionId(questionId)
                .options(
                        options.stream()
                                .map(MCQOptions::getOptionText)
                                .toList()
                )
                .build();
    }

}
