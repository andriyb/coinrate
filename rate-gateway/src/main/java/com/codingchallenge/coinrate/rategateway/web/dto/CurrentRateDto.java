package com.codingchallenge.coinrate.rategateway.web.dto;

public class CurrentRateDto {


    private String formattedRate;

    private String updateTime;

    public CurrentRateDto(String formattedRate, String updateTime) {
        this.formattedRate = formattedRate;
        this.updateTime = updateTime;
    }

    public String getFormattedRate() {
        return formattedRate;
    }

    public void setFormattedRate(String formattedRate) {
        this.formattedRate = formattedRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
