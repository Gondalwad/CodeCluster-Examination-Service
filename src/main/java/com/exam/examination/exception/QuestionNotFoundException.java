package com.exam.examination.exception;

public class QuestionNotFoundException extends RuntimeException {
    public QuestionNotFoundException(String questionNotFound) {
        super(questionNotFound);
    }
}
