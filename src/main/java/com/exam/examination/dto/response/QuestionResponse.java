package com.exam.examination.dto.response;

import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {

    private Long questionId;

    private String title;

    private String description;

    private QuestionType type;

    private Difficulty difficulty;

    private Integer marks;

    private Boolean isPublic;
}
