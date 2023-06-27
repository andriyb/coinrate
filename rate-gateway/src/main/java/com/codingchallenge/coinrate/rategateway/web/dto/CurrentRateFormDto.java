package com.codingchallenge.coinrate.rategateway.web.dto;

public class CurrentRateFormDto {


    private String displayRate;

    private String displayUpdateDateTime;

    public CurrentRateFormDto(String displayRate, String displayUpdateDateTime) {
        this.displayRate = displayRate;
        this.displayUpdateDateTime = displayUpdateDateTime;
    }

    public String getDisplayRate() {
        return displayRate;
    }

    public void setDisplayRate(String displayRate) {
        this.displayRate = displayRate;
    }

    public String getDisplayUpdateDateTime() {
        return displayUpdateDateTime;
    }

    public void setDisplayUpdateDateTime(String displayUpdateDateTime) {
        this.displayUpdateDateTime = displayUpdateDateTime;
    }
}
