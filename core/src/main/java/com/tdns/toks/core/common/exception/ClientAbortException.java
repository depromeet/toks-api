package com.tdns.toks.core.common.exception;

import lombok.Getter;

@Getter
public class ClientAbortException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Construct a new ClientAbortException with no other information.
     */
    public ClientAbortException() {
        super();
    }


    /**
     * Construct a new ClientAbortException for the specified message.
     *
     * @param message Message describing this exception
     */
    public ClientAbortException(String message) {
        super(message);
    }


    /**
     * Construct a new ClientAbortException for the specified throwable.
     *
     * @param throwable Throwable that caused this exception
     */
    public ClientAbortException(Throwable throwable) {
        super(throwable);
    }


    /**
     * Construct a new ClientAbortException for the specified message
     * and throwable.
     *
     * @param message Message describing this exception
     * @param throwable Throwable that caused this exception
     */
    public ClientAbortException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
