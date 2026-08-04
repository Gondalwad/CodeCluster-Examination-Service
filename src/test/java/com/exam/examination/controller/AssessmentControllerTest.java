package com.exam.examination.controller;

import com.exam.examination.dto.request.*;
import com.exam.examination.dto.response.*;
import com.exam.examination.enums.AssessmentStatus;
import com.exam.examination.service.AssessmentAttemptService;
import com.exam.examination.service.AssessmentQuestionService;
import com.exam.examination.service.AssessmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssessmentController.class)
class AssessmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AssessmentService assessmentService;

    @MockitoBean
    private AssessmentQuestionService AQService;

    @MockitoBean
    private AssessmentAttemptService AAService;

    private final UUID user = UUID.randomUUID();

    @Test
    void createAssessmentCreated() throws Exception {

        CreateAssessmentRequest request = CreateAssessmentRequest.builder()
                .title("Test")
                .durationMinutes(60)
                .totalMarks(100)
                .build();

        when(assessmentService.createAssessment(any()))
                .thenReturn(AssessmentResponse.builder().build());

        mockMvc.perform(post("/api/v1/assessments")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void createAssessmentBadRequest() throws Exception {

        CreateAssessmentRequest request = CreateAssessmentRequest.builder()
                .title("Test")
                .durationMinutes(60)
                .totalMarks(100)
                .build();

        when(assessmentService.createAssessment(any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/v1/assessments")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void submitQuestionsCreated() throws Exception {

        AQStructure q = new AQStructure();
        q.setQuestionId(1L);
        q.setDisplayOrder((short)1);
        q.setMarks(10);

        when(AQService.mapQuestionsToAssessment(anyList(), eq(1L)))
                .thenReturn(List.of());

        mockMvc.perform(post("/api/v1/assessments/1/questions")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(q))))
                .andExpect(status().isCreated());
    }

    @Test
    void submitQuestionsNotFound() throws Exception {

        AQStructure q = new AQStructure();
        q.setQuestionId(1L);
        q.setDisplayOrder((short)1);
        q.setMarks(10);

        when(AQService.mapQuestionsToAssessment(anyList(), eq(1L)))
                .thenThrow(new RuntimeException("Assessment not found"));

        mockMvc.perform(post("/api/v1/assessments/1/questions")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(q))))
                .andExpect(status().isNotFound());
    }

    @Test
    void submitQuestionsBadRequest() throws Exception {

        AQStructure q = new AQStructure();
        q.setQuestionId(1L);
        q.setDisplayOrder((short)1);
        q.setMarks(10);

        when(AQService.mapQuestionsToAssessment(anyList(), eq(1L)))
                .thenThrow(new RuntimeException("validation"));

        mockMvc.perform(post("/api/v1/assessments/1/questions")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(q))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStatusSuccess() throws Exception {
        UpdateAssessmentStatusRequest request = new UpdateAssessmentStatusRequest();
        request.setStatus(AssessmentStatus.PUBLISHED); // Set enum value to satisfy @NotNull/@Valid

        when(assessmentService.updateAssessmentStatus(eq(1L), any()))
                .thenReturn(AssessmentResponse.builder().build());

        mockMvc.perform(put("/api/v1/assessments/1/status")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void updateStatusNotFound() throws Exception {
        UpdateAssessmentStatusRequest request = new UpdateAssessmentStatusRequest();
        request.setStatus(AssessmentStatus.PUBLISHED); // Set enum value to satisfy @NotNull/@Valid

        when(assessmentService.updateAssessmentStatus(eq(1L), any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/v1/assessments/1/status")
                        .header("X-User-Id", user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAttemptCreated() throws Exception {

        when(AAService.startAttempt(any(), eq(1L)))
                .thenReturn(AssessmentAttemptResponse.builder().build());

        mockMvc.perform(post("/api/v1/assessments/1/attempts")
                        .header("X-User-Id", user))
                .andExpect(status().isCreated());
    }

    @Test
    void createAttemptConflict() throws Exception {

        when(AAService.startAttempt(any(), eq(1L)))
                .thenThrow(new RuntimeException("already exists"));

        mockMvc.perform(post("/api/v1/assessments/1/attempts")
                        .header("X-User-Id", user))
                .andExpect(status().isConflict());
    }

    @Test
    void createAttemptNotFound() throws Exception {

        when(AAService.startAttempt(any(), eq(1L)))
                .thenThrow(new RuntimeException("Assessment not found"));

        mockMvc.perform(post("/api/v1/assessments/1/attempts")
                        .header("X-User-Id", user))
                .andExpect(status().isNotFound());
    }
}