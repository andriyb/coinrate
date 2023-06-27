package com.codingchallenge.coinrate.rategateway.web.dto;

public class UpdateCurrentRateRequestDto {

    private String coinCode;

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }
}
