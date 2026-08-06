package com.exam.examination.service.impl;

import com.exam.examination.dto.request.AnswerRequest;
import com.exam.examination.dto.request.SubmitAssessmentRequest;
import com.exam.examination.dto.response.MessageResponse;
import com.exam.examination.entity.*;
import com.exam.examination.enums.QuestionType;
import com.exam.examination.enums.SubmissionStatus;
import com.exam.examination.exception.QuestionNotFoundException;
import com.exam.examination.repository.*;
import com.exam.examination.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final AssessmentRepository assessmentRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMCQRepository questionMCQRepository;
    private final SubmissionRepository submissionRepository;
    private final MCQSubmissionRepository mcqSubmissionRepository;
    private final DescriptiveSubmissionRepository descriptiveSubmissionRepository;

    @Override
    public MessageResponse submitAssessment(SubmitAssessmentRequest request) {

        Assessment assessment = assessmentRepository.findById(request.getAssessmentId())
                .orElseThrow(() ->
                        new RuntimeException("Assessment not found with id " + request.getAssessmentId()));

        for (AnswerRequest answer : request.getAnswers()) {

            Question question = questionRepository.findById(answer.getQuestionId())
                    .orElseThrow(() ->
                            new QuestionNotFoundException(
                                    "Question not found with id " + answer.getQuestionId()));

            Submission submission = Submission.builder()
                    .assessment(assessment)
                    .question(question)
                    .userId(request.getUserId())
                    .build();

            submission = submissionRepository.save(submission);

            switch (question.getType()) {

                case MCQ -> {

                    if (answer.getSelectedOptionId() == null) {
                        throw new IllegalArgumentException(
                                "Selected option is required for MCQ question "
                                        + question.getQuestionId());
                    }

                    MCQOptions option = questionMCQRepository
                            .findById(answer.getSelectedOptionId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "MCQ Option not found with id "
                                                    + answer.getSelectedOptionId()));

                    MCQSubmissionAnswer mcqSubmission =
                            MCQSubmissionAnswer.builder()
                                    .submission(submission)
                                    .option(option)
                                    .build();

                    mcqSubmissionRepository.save(mcqSubmission);
                }

                case DESCRIPTIVE -> {

                    if (answer.getAnswer() == null || answer.getAnswer().isBlank()) {
                        throw new IllegalArgumentException(
                                "Answer is required for descriptive question "
                                        + question.getQuestionId());
                    }

                    DescriptiveSubmission descriptiveSubmission =
                            DescriptiveSubmission.builder()
                                    .submission(submission)
                                    .responseText(answer.getAnswer())
                                    .build();

                    descriptiveSubmissionRepository.save(descriptiveSubmission);
                }

                default -> throw new IllegalArgumentException(
                        "Unsupported question type: " + question.getType());
            }
        }

        return MessageResponse.builder()
                .message("Exam submitted successfully.")
                .build();
    }
}