package com.exam.examination.service.impl;

import com.exam.examination.dto.request.AQStructure;
import com.exam.examination.dto.request.CreateAssessmentQuestionRequest;
import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.AssessmentQuestionResponse;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.entity.AssessmentQuestion;
import com.exam.examination.entity.AssessmentQuestionId;
import com.exam.examination.repository.AssessmentQuestionRepository;
import com.exam.examination.repository.AssessmentRepository;
import com.exam.examination.service.AssessmentQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentQuestionServiceImpl
        implements AssessmentQuestionService {

    private final AssessmentQuestionRepository repository;
    private final AssessmentRepository assessmentRepository;

    @Override
    public List<AssessmentQuestionResponse> mapQuestionsToAssessment(
            List<AQStructure> aqStructure,
            Long assessmentId
    ) {

        assessmentRepository.findByAssessmentId(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found with id: " + assessmentId)
                );

        List<AssessmentQuestion> entities = aqStructure.stream()
                .map(item -> {

                    AssessmentQuestion entity = new AssessmentQuestion();

                    entity.setId(
                            new AssessmentQuestionId(
                                    assessmentId,
                                    item.getQuestionId()
                            )
                    );

                    entity.setDisplayOrder(item.getDisplayOrder());
                    entity.setMarks(item.getMarks());

                    return entity;

                })
                .toList();

        repository.saveAll(entities);

        return entities.stream()
                .map(entity -> {

                    AssessmentQuestionResponse response =
                            new AssessmentQuestionResponse();

                    response.setAssessmentId(
                            entity.getId().getAssessmentId()
                    );

                    response.setQuestionId(
                            entity.getId().getQuestionId()
                    );

                    response.setDisplayOrder(
                            entity.getDisplayOrder()
                    );

                    response.setMarks(
                            entity.getMarks()
                    );

                    return response;

                })
                .toList();
    }
}