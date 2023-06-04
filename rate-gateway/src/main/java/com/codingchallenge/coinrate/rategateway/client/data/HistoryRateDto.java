package com.codingchallenge.coinrate.rategateway.client.data;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoryRateDto extends RateDto {

    private LocalDate rateDate;

    public HistoryRateDto(String coinCode, String currencyCode, String coinName, String coinSymbol,
                          BigDecimal currentRate, LocalDate rateDate) {

        super(coinCode, currencyCode, coinName, coinSymbol, currentRate);
        this.rateDate = rateDate;
    }

    public LocalDate getRateDate() {
        return rateDate;
    }

    public void setRateDate(LocalDate rateDate) {
        this.rateDate = rateDate;
    }
}
