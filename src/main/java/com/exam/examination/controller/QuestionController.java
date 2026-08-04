package com.exam.examination.controller;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionResponse> createQuestion(
            @Valid @RequestBody CreateQuestionRequest request) {

        try {

            QuestionResponse response =
                    questionService.createQuestion(request);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<QuestionResponse>> getQuestions(

            @RequestHeader("X-User-Id")
            UUID userId,

            @RequestParam(required = false)
            Difficulty difficulty,

            @RequestParam(required = false)
            QuestionType type
    ) {

        try {

            return ResponseEntity.ok(
                    questionService.getQuestions(difficulty, type)
            );

        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getFullQuestion(
            @RequestHeader("X-User-Id")
            UUID userId,

            @PathVariable Long questionId
    ) {

        try {

            return ResponseEntity.ok(
                    questionService.getFullQuestion(questionId)
            );

        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}