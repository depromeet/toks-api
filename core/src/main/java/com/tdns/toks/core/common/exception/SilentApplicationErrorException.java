package com.tdns.toks.core.common.exception;

import lombok.Getter;

@Getter
public class SilentApplicationErrorException extends ApplicationErrorException {

    public SilentApplicationErrorException(ApplicationErrorType responseStatusType) {
        super(responseStatusType);
    }

    public SilentApplicationErrorException(ApplicationErrorType responseStatusType, String... args) {
        super(responseStatusType, args);
    }

    public SilentApplicationErrorException(ApplicationErrorType responseStatusType, Throwable t) {
        super(responseStatusType, t);
    }

    public SilentApplicationErrorException(ApplicationErrorType responseStatusType, Throwable t, String customMessage) {
        super(responseStatusType, t, customMessage);
    }

}
