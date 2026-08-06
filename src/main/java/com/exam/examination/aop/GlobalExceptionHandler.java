package com.exam.examination.aop;

import com.exam.examination.dto.response.ErrorResponseDto;
import com.exam.examination.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    ///Handles exception if question not found
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleQuestionNotFound(QuestionNotFoundException e){
        ErrorResponseDto errorDto = new ErrorResponseDto(
                404,
                e.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }
}
