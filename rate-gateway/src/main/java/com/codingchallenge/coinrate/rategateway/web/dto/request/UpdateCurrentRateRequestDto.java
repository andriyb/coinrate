package com.codingchallenge.coinrate.rategateway.web.dto.request;

/**
 * Data transfer object for requesting an update of the current cryptocurrency rate.
 */
public class UpdateCurrentRateRequestDto {

    private String coinCode;

    /**
     * Returns the coin code.
     *
     * @return The coin code.
     */
    public String getCoinCode() {
        return coinCode;
    }

    /**
     * Sets the coin code.
     *
     * @param coinCode The coin code.
     */
    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }
}
