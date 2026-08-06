package com.exam.examination.controller;

import com.exam.examination.dto.response.AssessmentAttemptResponse;
import com.exam.examination.service.AssessmentAttemptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AttemptController.class)
class AttemptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AssessmentAttemptService AAService;

    private final UUID user = UUID.randomUUID();

    @Test
    void fetchAttemptSuccess() throws Exception {

        when(AAService.fetchAttempt(1L))
                .thenReturn(AssessmentAttemptResponse.builder().build());

        mockMvc.perform(get("/api/v1/attempts/1")
                        .header("X-User-Id", user))
                .andExpect(status().isOk());
    }

    @Test
    void fetchAttemptNotFound() throws Exception {

        when(AAService.fetchAttempt(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/v1/attempts/1")
                        .header("X-User-Id", user))
                .andExpect(status().isNotFound());
    }

    @Test
    void submitAttemptSuccess() throws Exception {

        when(AAService.submitAttempt(1L))
                .thenReturn(AssessmentAttemptResponse.builder().build());

        mockMvc.perform(post("/api/v1/attempts/1/submit")
                        .header("X-User-Id", user))
                .andExpect(status().isOk());
    }

    @Test
    void submitAttemptConflict() throws Exception {

        when(AAService.submitAttempt(1L))
                .thenThrow(new RuntimeException("Assessment Attempt has already been submitted."));

        mockMvc.perform(post("/api/v1/attempts/1/submit")
                        .header("X-User-Id", user))
                .andExpect(status().isConflict());
    }

    @Test
    void submitAttemptNotFound() throws Exception {

        when(AAService.submitAttempt(1L))
                .thenThrow(new RuntimeException("Attempt not found"));

        mockMvc.perform(post("/api/v1/attempts/1/submit")
                        .header("X-User-Id", user))
                .andExpect(status().isNotFound());
    }
}