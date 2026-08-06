package com.exam.examination.dto.request;

import com.exam.examination.enums.QuestionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerRequest {

    private Long questionId;

    private QuestionType type;

    // Used only for MCQ questions
    private Long selectedOptionId;

    // Used only for descriptive questions
    private String answer;
}