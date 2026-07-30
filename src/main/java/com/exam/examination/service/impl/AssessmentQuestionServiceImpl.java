package com.exam.examination.service.impl;

import com.exam.examination.dto.request.AQStructure;
import com.exam.examination.dto.request.CreateAssessmentQuestionRequest;
import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.AssessmentQuestionResponse;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.entity.AssessmentQuestion;
import com.exam.examination.entity.AssessmentQuestionId;
import com.exam.examination.repository.AssessmentQuestionRepository;
import com.exam.examination.service.AssessmentQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentQuestionServiceImpl implements AssessmentQuestionService {

    private final AssessmentQuestionRepository AQRepository;

    @Override
    public List<AssessmentQuestionResponse> mapQuestionsToAssessment(
            List<AQStructure> aqStructure,
            Long assessmentId
    ){

        List<AssessmentQuestion> mappings = aqStructure
                .stream(

                );

        List<AssessmentQuestion> res = AQRepository.saveAll(mappings);

        return res.stream()
                .map(aqObj -> AssessmentQuestionResponse.builder()
                        .assessmentId(aqObj.getId().getAssessmentId())
                        .questionId(aqObj.getId().getQuestionId())
                        .displayOrder(aqObj.getDisplayOrder())
                        .marks(aqObj.getMarks())
                        .build())
                .toList();



    }

}
