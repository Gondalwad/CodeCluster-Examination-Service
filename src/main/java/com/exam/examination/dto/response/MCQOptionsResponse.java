package com.exam.examination.dto.response;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MCQOptionsResponse {

    private Long questionId;
    private List<String> options;

}