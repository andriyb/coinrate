package com.codingchallenge.coinrate.rategateway.service.exception;

public class NoSuchCoinExitsException extends RuntimeException {

    public NoSuchCoinExitsException() {
        super();
    }

    public NoSuchCoinExitsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchCoinExitsException(final String message) {
        super(message);
    }

    public NoSuchCoinExitsException(final Throwable cause) {
        super(cause);
    }
}