package com.codingchallenge.coinrate.rategateway.service.exception;

public class NoSuchCurrencyExistsException  extends RuntimeException {

    public NoSuchCurrencyExistsException() {
        super();
    }

    public NoSuchCurrencyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchCurrencyExistsException(final String message) {
        super(message);
    }

    public NoSuchCurrencyExistsException(final Throwable cause) {
        super(cause);
    }
}