package com.exam.examination.dto.request;

import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateQuestionRequest {

    @NotNull
    private Long questionId;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private QuestionType type;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    @Positive
    private Integer marks;

    @NotNull
    private Boolean isPublic;
}
