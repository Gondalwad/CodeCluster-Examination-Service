package com.exam.examination.controller;


import com.exam.examination.dto.response.AssessmentAttemptResponse;
import com.exam.examination.service.AssessmentAttemptService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/attempts")
@RequiredArgsConstructor
public class AttemptController {

    private final AssessmentAttemptService AAService;

    @GetMapping("/{attemptId}")
    public ResponseEntity<AssessmentAttemptResponse> fetchAttempt(
            @RequestHeader("X-User-Id") UUID uuid,
            @PathVariable Long attemptId
    ) {

        try {
            AssessmentAttemptResponse response = AAService.fetchAttempt(attemptId);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<AssessmentAttemptResponse> submitAttempt(
            @RequestHeader("X-User-Id") UUID uuid,
            @PathVariable Long attemptId
    ) {

        try {
            AssessmentAttemptResponse response = AAService.submitAttempt(attemptId);
            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {

            if ("Assessment Attempt has already been submitted."
                    .equals(ex.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}