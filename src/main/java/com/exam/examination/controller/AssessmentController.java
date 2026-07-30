package com.exam.examination.controller;
import com.exam.examination.dto.request.CreateAssessmentRequest;
import com.exam.examination.dto.response.AssessmentResponse;
import com.exam.examination.dto.response.QuestionResponse;
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


}
