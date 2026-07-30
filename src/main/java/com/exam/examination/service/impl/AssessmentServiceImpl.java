package com.exam.examination.service.impl;


import com.exam.examination.dto.request.CreateAssessmentRequest;
import com.exam.examination.dto.request.UpdateAssessmentStatusRequest;
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


    @Override
    public AssessmentResponse updateAssessmentStatus(
            Long assessmentId,
            UpdateAssessmentStatusRequest request
    ) {

        Assessment assessment = repository.findByAssessmentId(assessmentId)
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found")
                );

        assessment.setStatus(request.getStatus());

        Assessment savedAssessment = repository.save(assessment);

        return AssessmentResponse.builder()
                .assessmentId(savedAssessment.getAssessmentId())
                .title(savedAssessment.getTitle())
                .description(savedAssessment.getDescription())
                .durationMinutes(savedAssessment.getDurationMinutes())
                .totalMarks(savedAssessment.getTotalMarks())
                .status(savedAssessment.getStatus())
                .startTime(savedAssessment.getStartTime())
                .endTime(savedAssessment.getEndTime())
                .createdAt(savedAssessment.getCreatedAt())
                .updatedAt(savedAssessment.getUpdatedAt())
                .build();
    }
}
