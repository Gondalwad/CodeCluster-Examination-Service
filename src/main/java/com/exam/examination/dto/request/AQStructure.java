package com.exam.examination.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AQStructure {
    @NotNull
    private Long questionId;

    @NotNull
    @Min(1)
    private Short displayOrder;

    @NotNull
    @Min(1)
    @Positive
    private Integer marks;

}
