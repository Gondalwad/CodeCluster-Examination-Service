package com.exam.examination.controller;

import com.exam.examination.dto.request.CreateQuestionRequest;
import com.exam.examination.dto.response.QuestionResponse;
import com.exam.examination.enums.Difficulty;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
class QuestionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    QuestionService questionService;

    UUID user = UUID.randomUUID();

    @Test
    void createQuestionCreated() throws Exception {

        CreateQuestionRequest request = CreateQuestionRequest.builder()
                .questionId(1L)
                .title("title")
                .description("desc")
                .difficulty(Difficulty.EASY)
                .type(QuestionType.MCQ)
                .marks(5)
                .isPublic(true)
                .build();

        when(questionService.createQuestion(any()))
                .thenReturn(QuestionResponse.builder().build());

        mockMvc.perform(post("/api/v1/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createQuestionBadRequest() throws Exception {

        when(questionService.createQuestion(any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/v1/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(CreateQuestionRequest.builder()
                                .questionId(1L)
                                .title("t")
                                .description("d")
                                .difficulty(Difficulty.EASY)
                                .type(QuestionType.MCQ)
                                .marks(5)
                                .isPublic(true)
                                .build())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getQuestionsSuccess() throws Exception {

        when(questionService.getQuestions(null, null))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/v1/questions")
                        .header("X-User-Id", user))
                .andExpect(status().isOk());
    }

    @Test
    void getQuestionsBadRequest() throws Exception {

        when(questionService.getQuestions(null, null))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/questions")
                        .header("X-User-Id", user))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getQuestionSuccess() throws Exception {

        when(questionService.getFullQuestion(1L))
                .thenReturn(QuestionResponse.builder().build());

        mockMvc.perform(get("/api/v1/questions/1")
                        .header("X-User-Id", user))
                .andExpect(status().isOk());
    }

    @Test
    void getQuestionNotFound() throws Exception {

        when(questionService.getFullQuestion(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/questions/1")
                        .header("X-User-Id", user))
                .andExpect(status().isNotFound());
    }
}