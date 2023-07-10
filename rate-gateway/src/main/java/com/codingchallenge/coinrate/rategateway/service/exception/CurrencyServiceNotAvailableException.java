package com.codingchallenge.coinrate.rategateway.service.exception;

public class CurrencyServiceNotAvailableException extends ServiceNotAvailableException {

    public CurrencyServiceNotAvailableException() {
        super();
    }

    public CurrencyServiceNotAvailableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CurrencyServiceNotAvailableException(final String message) {
        super(message);
    }

    public CurrencyServiceNotAvailableException(final Throwable cause) {
        super(cause);
    }
}
