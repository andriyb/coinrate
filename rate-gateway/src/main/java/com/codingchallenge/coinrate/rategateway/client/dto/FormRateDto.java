package com.codingchallenge.coinrate.rategateway.client.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FormRateDto extends CurrentRateDto {

    private List<HistoryRateDto> history;

    public FormRateDto() {

    }

    public FormRateDto(String coinCode, String currencyCode, String coinName, String coinSymbol,
                       BigDecimal currentRate, LocalDateTime rateDateTime, List<HistoryRateDto> history) {

        super(coinCode, currencyCode, coinName, coinSymbol, currentRate, rateDateTime);
        this.history = history;
    }

    public List<HistoryRateDto> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryRateDto> history) {
        this.history = history;
    }
}