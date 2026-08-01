package com.exam.examination.controller;
import com.exam.examination.dto.request.AQStructure;
import com.exam.examination.dto.request.CreateAssessmentQuestionRequest;
import com.exam.examination.dto.request.CreateAssessmentRequest;
import com.exam.examination.dto.request.UpdateAssessmentStatusRequest;
import com.exam.examination.dto.response.AssessmentAttemptResponse;
import com.exam.examination.dto.response.AssessmentQuestionResponse;
import com.exam.examination.dto.response.AssessmentResponse;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.service.AssessmentAttemptService;
import com.exam.examination.service.AssessmentQuestionService;
import com.exam.examination.service.AssessmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assessments")
@RequiredArgsConstructor
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentQuestionService AQService;
    private final AssessmentAttemptService AAService;

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(
            @RequestHeader("X-User-Id")
            UUID uuid,
            @Valid @RequestBody CreateAssessmentRequest request
    ){
        AssessmentResponse response = assessmentService.createAssessment(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/{assessmentId}/questions")
    public ResponseEntity<List<AssessmentQuestionResponse>> submitQuestionsToAssessment(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @Valid @RequestBody List<AQStructure> aqStructure,

            @PathVariable Long assessmentId
            ){

        List<AssessmentQuestionResponse> response = AQService.mapQuestionsToAssessment(aqStructure, assessmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{assessmentId}/status")
    public ResponseEntity<AssessmentResponse> updateAssessmentStatus(

            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable Long assessmentId,

            @Valid
            @RequestBody UpdateAssessmentStatusRequest request
    ) {

        AssessmentResponse response =
                assessmentService.updateAssessmentStatus(
                        assessmentId,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{assessmentId}/attempts")
    public ResponseEntity<AssessmentAttemptResponse> createAssessmentAttempt(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable Long assessmentId

    ){
        AssessmentAttemptResponse response = AAService.startAttempt(uuid, assessmentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
