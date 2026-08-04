package com.exam.examination;

import com.exam.examination.repository.AssessmentAttemptRepository;
import com.exam.examination.repository.AssessmentQuestionRepository;
import com.exam.examination.repository.AssessmentRepository;
import com.exam.examination.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ExaminationApplicationTests {

    @MockitoBean
    private AssessmentRepository assessmentRepository;

    @MockitoBean
    private QuestionRepository questionRepository;

    @MockitoBean
    private AssessmentQuestionRepository assessmentQuestionRepository;

    @MockitoBean
    private AssessmentAttemptRepository assessmentAttemptRepository;

    @Test
    void contextLoads() {
    }
}