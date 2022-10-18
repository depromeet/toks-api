package com.tdns.toks.core.common.exception;

public class ExternalApiException extends RuntimeException {

    private static final long serialVersionUID = 2768382570988752705L;

    public ExternalApiException() {
        super();
    }

    public ExternalApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public ExternalApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExternalApiException(String message) {
        super(message);
    }

    public ExternalApiException(Throwable cause) {
        super(cause);
    }

}
