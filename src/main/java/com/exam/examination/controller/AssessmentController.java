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
    ) {
        try {
            AssessmentResponse response = assessmentService.createAssessment(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{assessmentId}/questions")
    public ResponseEntity<List<AssessmentQuestionResponse>> submitQuestionsToAssessment(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @Valid @RequestBody List<AQStructure> aqStructure,

            @PathVariable Long assessmentId
    ) {

        try {

            List<AssessmentQuestionResponse> response =
                    AQService.mapQuestionsToAssessment(aqStructure, assessmentId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException ex) {

            if (ex.getMessage().contains("Assessment")
                    || ex.getMessage().contains("Question")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{assessmentId}/status")
    public ResponseEntity<AssessmentResponse> updateAssessmentStatus(

            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable Long assessmentId,

            @Valid
            @RequestBody UpdateAssessmentStatusRequest request
    ) {

        try {

            AssessmentResponse response =
                    assessmentService.updateAssessmentStatus(
                            assessmentId,
                            request
                    );

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Api is accessible by faculty and institute admin to create Assesment.
     * @param uuid
     * @param assessmentId
     * @return
     */
    @PostMapping("/{assessmentId}/attempts")
    public ResponseEntity<AssessmentAttemptResponse> createAssessmentAttempt(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable Long assessmentId

    ) {

        try {

            AssessmentAttemptResponse response =
                    AAService.startAttempt(uuid, assessmentId);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException ex) {

            if (ex.getMessage().contains("already")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
