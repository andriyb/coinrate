package com.codingchallenge.coinrate.rategateway.service.exception;

public class LocaleServiceNotAvailableException extends ServiceNotAvailableException {

    public LocaleServiceNotAvailableException() {
        super();
    }

    public LocaleServiceNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public LocaleServiceNotAvailableException(final String message) {
        super(message);
    }

    public LocaleServiceNotAvailableException(final Throwable cause) {
        super(cause);
    }
}
