package com.codingchallenge.coinrate.rategateway.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RateDto {

    private String coinCode;

    private String currencyCode;

    private String coinName;

    private String coinSymbol;

    private BigDecimal currentRate;

    public RateDto() {}

    public RateDto(String coinCode, String currencyCode, String coinName, String coinSymbol, BigDecimal currentRate) {
        this.coinCode = coinCode;
        this.currencyCode = currencyCode;
        this.coinName = coinName;
        this.coinSymbol = coinSymbol;
        this.currentRate = currentRate;
    }


    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public BigDecimal getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(BigDecimal currentRate) {
        this.currentRate = currentRate;
    }

}
