package com.codingchallenge.coinrate.rategateway.web.dto;

import java.time.LocalDate;

public class HistoryRateDTO {

    private LocalDate date;

    private String rate;

    public HistoryRateDTO(LocalDate date, String rate) {
        this.date = date;
        this.rate = rate;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
