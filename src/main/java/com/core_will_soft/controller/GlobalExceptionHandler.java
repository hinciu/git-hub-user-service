package com.core_will_soft.controller;

import com.core_will_soft.dto.error.ErrorDetail;
import com.core_will_soft.dto.error.ErrorResponse;
import com.core_will_soft.dto.error.ErrorResponseDTO;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static com.core_will_soft.dto.error.ErrorCode.CONSTRAINT_VIOLATION;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(final MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        val details = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> ErrorDetail.builder()
                        .target(e.getField())
                        .message(StringUtils.capitalize(e.getDefaultMessage()))
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseDTO(new ErrorResponse(CONSTRAINT_VIOLATION, details)));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDTO> handle(final ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(UNPROCESSABLE_ENTITY)
                .body(new ErrorResponseDTO(new ErrorResponse(CONSTRAINT_VIOLATION, ex.getConstraintViolations().stream()
                        .map(v -> ErrorDetail.builder()
                                .target(v.getPropertyPath().toString())
                                .message(v.getMessage())
                                .build())
                        .collect(Collectors.toList()))));

    }
}
