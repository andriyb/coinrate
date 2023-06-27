package com.codingchallenge.coinrate.rategateway.web.dto;

public class ChangeCoinRequestDto extends UpdateCurrentRateRequestDto {

    private Integer daysCount;

    public Integer getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount;
    }
}
