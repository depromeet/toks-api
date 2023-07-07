package com.tdns.toks.core.common.exception;

import lombok.Getter;

@Getter
public class ApplicationErrorException extends RuntimeException {
    private ApplicationErrorType errorType;
    private String customMessage;
    private String[] args;
    private Object data;

    public ApplicationErrorException(ApplicationErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ApplicationErrorException(ApplicationErrorType errorType, Object data, String... args) {
        super(errorType.getMessage());
        this.data = data;
        this.args = args;
        this.errorType = errorType;
    }

    public ApplicationErrorException(ApplicationErrorType errorType, Throwable t) {
        super(t);
        this.errorType = errorType;
    }

    public ApplicationErrorException(ApplicationErrorType errorType, Throwable t, String customMessage) {
        super(t);
        this.errorType = errorType;
        this.customMessage = customMessage;
    }
}
