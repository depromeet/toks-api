package com.tdns.toks.core.common.exception;

import com.tdns.toks.core.common.model.entity.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

@Primary
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleException(WebRequest request, Exception ex) {
        return handleExceptionInternal(ex, ex, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = ClientAbortException.class)
    public ResponseEntity<Object> handleClientAbortException(WebRequest request, ClientAbortException ex) {
        return handleApplicationRuntimeException(request, new SilentApplicationErrorException(ApplicationErrorType.CLIENT_ABORT, ex));
    }

    @ExceptionHandler(value = ApplicationErrorException.class)
    public ResponseEntity<Object> handleApplicationErrorException(WebRequest request, ApplicationErrorException ex) {
        return handleApplicationRuntimeException(request, ex);
    }

    @ExceptionHandler(value = SilentApplicationErrorException.class)
    public ResponseEntity<Object> handleSilentApplicationErrorException(WebRequest request, SilentApplicationErrorException ex) {
        return handleApplicationRuntimeException(request, ex);
    }


    private <T extends ApplicationErrorException> ResponseEntity<Object> handleApplicationRuntimeException(WebRequest request, T ex) {
        var responseStatusType = ex.getResponseStatusType();
        var data = ex.getData();
        var errorMessage = StringUtils.isNotEmpty(ex.getCustomMessage()) ? ex.getCustomMessage() : ex.getMessage();

        var errorResponse = ErrorResponse.builder()
                .status(responseStatusType.getHttpStatus().name())
                .data(data)
                .message(errorMessage)
                .build();

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), responseStatusType.getHttpStatus(), request);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(WebRequest request, EmptyResultDataAccessException e) {
        e.printStackTrace();
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.COULDNT_FIND_ANY_DATA, e));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(WebRequest request, RuntimeException ex) {
        ex.printStackTrace();
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.INTERNAL_ERROR, ex));
    }

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<Object> handleValidationException(WebRequest request, ValidationException ex) {
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.INVALID_DATA_ARGUMENT, ex));
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public ResponseEntity<Object> handleHttpMessageConversionException(WebRequest request, HttpMessageConversionException ex) {
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.INVALID_DATA_ARGUMENT, ex));
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(WebRequest request, ConstraintViolationException ex) {
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.INVALID_DATA_ARGUMENT, ex));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        return handleApplicationErrorException(request, new ApplicationErrorException(ApplicationErrorType.INVALID_DATA_ARGUMENT, ex, ex.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        if (ex instanceof SilentApplicationErrorException) {
            var aEE = (ApplicationErrorException) ex;
            log.error("SilentApplicationErrorException.{}", aEE.getResponseStatusType().name());
        } else if (ex instanceof ApplicationErrorException) {
            var aEE = (ApplicationErrorException) ex;
            log.error("ApplicationErrorType.{}", aEE.getResponseStatusType().name(), ex);
        } else {
            log.error("", ex);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
