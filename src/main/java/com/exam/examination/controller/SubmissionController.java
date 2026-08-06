package com.exam.examination.controller;

import com.exam.examination.dto.request.SubmitAssessmentRequest;
import com.exam.examination.dto.response.MessageResponse;
import com.exam.examination.exception.QuestionNotFoundException;
import com.exam.examination.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submission")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping("/submitAssessment")
    public ResponseEntity<MessageResponse> submitAssessment(
            @RequestBody SubmitAssessmentRequest request
    ) {

        try {

            return ResponseEntity.ok(
                    submissionService.submitAssessment(request)
            );

        } catch (QuestionNotFoundException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            MessageResponse.builder()
                                    .message(ex.getMessage())
                                    .build()
                    );

        } catch (IllegalArgumentException ex) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            MessageResponse.builder()
                                    .message(ex.getMessage())
                                    .build()
                    );

        } catch (RuntimeException ex) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(
                            MessageResponse.builder()
                                    .message(ex.getMessage())
                                    .build()
                    );

        } catch (Exception ex) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            MessageResponse.builder()
                                    .message("An unexpected error occurred while submitting the assessment.")
                                    .build()
                    );
        }
    }
}