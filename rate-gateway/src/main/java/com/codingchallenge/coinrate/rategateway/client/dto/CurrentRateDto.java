package com.codingchallenge.coinrate.rategateway.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrentRateDto extends RateDto {

    private LocalDateTime rateDateTime;


    public CurrentRateDto(){
    }

    public CurrentRateDto(String coinCode, String currencyCode, String coinName, String coinSymbol,
                          BigDecimal currentRate, LocalDateTime rateDateTime) {

        super(coinCode, currencyCode, coinName, coinSymbol, currentRate);
        this.rateDateTime = rateDateTime;
    }

    public LocalDateTime getRateDateTime() {
        return rateDateTime;
    }

    public void setRateDateTime(LocalDateTime rateDateTime) {
        this.rateDateTime = rateDateTime;
    }
}
