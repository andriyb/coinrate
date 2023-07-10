package com.codingchallenge.coinrate.rategateway.service.exception;

public class ServiceNotAvailableException extends RuntimeException {

    public ServiceNotAvailableException() {
        super();
    }

    public ServiceNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ServiceNotAvailableException(final String message) {
        super(message);
    }

    public ServiceNotAvailableException(final Throwable cause) {
        super(cause);
    }
}
