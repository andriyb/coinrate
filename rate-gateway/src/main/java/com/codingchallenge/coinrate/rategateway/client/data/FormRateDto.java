package com.codingchallenge.coinrate.rategateway.client.data;

import java.math.BigDecimal;
import java.util.List;

public class FormRateDto extends RateDto {

    private List<HistoryRateDto> history;

    public FormRateDto(String coinCode, String currencyCode, String coinName, String coinSymbol,
                       BigDecimal currentRate, List<HistoryRateDto> history) {

        super(coinCode, currencyCode, coinName, coinSymbol, currentRate);
        this.history = history;
    }

    public List<HistoryRateDto> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryRateDto> history) {
        this.history = history;
    }
}

