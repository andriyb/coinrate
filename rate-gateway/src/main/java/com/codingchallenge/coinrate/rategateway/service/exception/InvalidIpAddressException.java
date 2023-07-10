package com.codingchallenge.coinrate.rategateway.service.exception;

public class InvalidIpAddressException extends RuntimeException {

    public InvalidIpAddressException() {
        super();
    }

    public InvalidIpAddressException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidIpAddressException(final String message) {
        super(message);
    }

    public InvalidIpAddressException(final Throwable cause) {
        super(cause);
    }
}