package com.tdns.toks.core.common.exception;

import lombok.Getter;

@Getter
public class ApplicationErrorException extends RuntimeException {
    private ApplicationErrorType responseStatusType;
    private String customMessage;
    private String[] args;
    private Object data;

    public ApplicationErrorException(ApplicationErrorType responseStatusType) {
        super(responseStatusType.getMessage());
        this.responseStatusType = responseStatusType;
    }

    public ApplicationErrorException(ApplicationErrorType responseStatusType, Object data, String... args) {
        super(responseStatusType.getMessage());
        this.data = data;
        this.args = args;
        this.responseStatusType = responseStatusType;
    }

    public ApplicationErrorException(ApplicationErrorType responseStatusType, Throwable t) {
        super(t);
        this.responseStatusType = responseStatusType;
    }

    public ApplicationErrorException(ApplicationErrorType responseStatusType, Throwable t, String customMessage) {
        super(t);
        this.responseStatusType = responseStatusType;
        this.customMessage = customMessage;
    }
}
