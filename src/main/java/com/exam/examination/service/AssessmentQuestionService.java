package com.exam.examination.service;

import com.exam.examination.dto.request.AQStructure;
import com.exam.examination.dto.request.CreateAssessmentQuestionRequest;
import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.AssessmentQuestionResponse;

import java.util.List;

public interface AssessmentQuestionService {

    List<AssessmentQuestionResponse> mapQuestionsToAssessment(
            List<AQStructure> aqStructure,
            Long assessmentId
    );

}
