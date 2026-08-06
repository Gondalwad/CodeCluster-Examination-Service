package com.exam.examination.controller;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.MCQOptionsResponse;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.service.QuestionMCQService;
import com.exam.examination.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mcq")
@RequiredArgsConstructor
public class QuestionMCQController {

    private final QuestionMCQService questionMCQService;

    @GetMapping("/{questionId}")
    public ResponseEntity<MCQOptionsResponse> getOptions(
            @RequestHeader("X-User-Id")
            UUID uuid,

            @PathVariable Long questionId
    ){
        try {
            MCQOptionsResponse response = questionMCQService.fetchOptions(questionId);
            return ResponseEntity.status(HttpStatus.FOUND).body(response);
        }
        catch(RuntimeException e) {
            if(e.getMessage().contains("not found")){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            return ResponseEntity.badRequest().build();
        }
    }
}
