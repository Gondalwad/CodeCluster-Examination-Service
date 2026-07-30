package com.exam.examination.service.impl;


import com.exam.examination.dto.request.CreateAssessmentRequest;
import com.exam.examination.dto.response.AssessmentResponse;
import com.exam.examination.entity.Assessment;
import com.exam.examination.repository.AssessmentRepository;
import com.exam.examination.service.AssessmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentRepository repository;

    @Override
    public AssessmentResponse createAssessment(CreateAssessmentRequest request) {

        if(repository.existsByTitle(request.getTitle())){
            throw new RuntimeException("Assessment already exists");
        }

        Assessment assessment = Assessment.builder()
                .title(request.getTitle())
                .durationMinutes(request.getDurationMinutes())
                .totalMarks(request.getTotalMarks())
                .build();

        Assessment savedQuestion = repository.save(assessment);

        return AssessmentResponse.builder()
                .assessmentId(savedQuestion.getAssessmentId())
                .title(savedQuestion.getTitle())
                .durationMinutes(savedQuestion.getDurationMinutes())
                .totalMarks(savedQuestion.getTotalMarks())
                .build();
    }
}
