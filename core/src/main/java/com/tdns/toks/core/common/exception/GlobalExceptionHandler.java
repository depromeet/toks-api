package com.tdns.toks.core.common.exception;

import com.tdns.toks.core.common.model.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@Primary
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ApplicationErrorException.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationErrorException(WebRequest request, ApplicationErrorException e) {
        log.error("ApplicationErrorException {}", e.getMessage());
        var response = new ErrorResponseDto(e.getErrorType().name(), e.getMessage());
        return new ResponseEntity<>(response, e.getErrorType().getHttpStatus());
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorResponseDto> handleEmptyResultDataAccessException(WebRequest request, EmptyResultDataAccessException e) {
        log.error("EmptyResultDataAccessException {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.COULDNT_FIND_ANY_DATA.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.COULDNT_FIND_ANY_DATA.getHttpStatus());
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(WebRequest request, ValidationException e) {
        log.error("ValidationException {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public ResponseEntity<Object> handleHttpMessageConversionException(WebRequest request, HttpMessageConversionException e) {
        log.error("HttpMessageConversionException {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(WebRequest request, ConstraintViolationException e) {
        log.error("ConstraintViolationException {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INVALID_DATA_ARGUMENT.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INVALID_DATA_ARGUMENT.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseDto> handleException(WebRequest request, Exception e) {
        log.error("Exception {}", e.getMessage());
        var response = new ErrorResponseDto(ApplicationErrorType.INTERNAL_ERROR.name(), e.getMessage());
        return new ResponseEntity<>(response, ApplicationErrorType.INTERNAL_ERROR.getHttpStatus());
    }
}
