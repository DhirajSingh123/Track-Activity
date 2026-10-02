package com.track.activity.exception;

import com.track.activity.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(
            IllegalArgumentException exception) {

        ApiResponse<Void> response =
                new ApiResponse<>(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage(),
                        null
                );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}