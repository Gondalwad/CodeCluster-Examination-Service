package com.exam.examination.service;

import com.exam.examination.dto.response.MCQOptionsResponse;
import com.exam.examination.entity.MCQOptions;

public interface QuestionMCQService {

    MCQOptionsResponse fetchOptions(Long questionId);
}
