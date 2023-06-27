package com.codingchallenge.coinrate.rategateway.web.dto;

public class HistoryRateFormDto {

    private String displayRateDate;

    private String displayRate;

    public HistoryRateFormDto(String displayRate, String displayRateDate) {
        this.displayRate = displayRate;
        this.displayRateDate = displayRateDate;
    }

    public String getDisplayRateDate() {
        return displayRateDate;
    }

    public void setDisplayRateDate(String displayRateDate) {
        this.displayRateDate = displayRateDate;
    }

    public String getDisplayRate() {
        return displayRate;
    }

    public void setDisplayRate(String displayRate) {
        this.displayRate = displayRate;
    }
}
