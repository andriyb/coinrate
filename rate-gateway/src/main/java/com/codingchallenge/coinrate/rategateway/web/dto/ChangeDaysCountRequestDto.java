package com.codingchallenge.coinrate.rategateway.web.dto;

import java.util.List;

public class ChangeDaysCountRequestDto {

    private List<HistoryRateFormDto> historyRatesForm;

    public List<HistoryRateFormDto> getHistoryRatesForm() {
        return historyRatesForm;
    }

    public void setHistoryRatesForm(List<HistoryRateFormDto> historyRatesForm) {
        this.historyRatesForm = historyRatesForm;
    }
}
