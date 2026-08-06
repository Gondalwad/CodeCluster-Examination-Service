package com.exam.examination.dto.response;

import com.exam.examination.enums.QuestionType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssessmentQuestionTypeResponse {

    private Long questionId;

    private QuestionType questionType;
}