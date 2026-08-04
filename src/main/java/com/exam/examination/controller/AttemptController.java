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
            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable
            Long attemptId
    ){

        AssessmentAttemptResponse response = AAService.fetchAttempt(attemptId);
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<AssessmentAttemptResponse> submitAttempt(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable
            Long attemptId
    ){
        AssessmentAttemptResponse response = AAService.submitAttempt(attemptId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
