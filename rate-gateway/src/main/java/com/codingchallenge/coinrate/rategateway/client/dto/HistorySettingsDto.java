package com.codingchallenge.coinrate.rategateway.client.dto;

public class HistorySettingsDto {

    private Integer historyDaysCountLimit;

    private Integer historyDaysCountDefault;

    public HistorySettingsDto(Integer historyDaysCountLimit, Integer historyDaysCountDefault) {
        this.historyDaysCountLimit = historyDaysCountLimit;
        this.historyDaysCountDefault = historyDaysCountDefault;
    }

    public Integer getHistoryDaysCountLimit() {
        return historyDaysCountLimit;
    }

    public void setHistoryDaysCountLimit(Integer historyDaysCountLimit) {
        this.historyDaysCountLimit = historyDaysCountLimit;
    }

    public Integer getHistoryDaysCountDefault() {
        return historyDaysCountDefault;
    }

    public void setHistoryDaysCountDefault(Integer historyDaysCountDefault) {
        this.historyDaysCountDefault = historyDaysCountDefault;
    }
}
