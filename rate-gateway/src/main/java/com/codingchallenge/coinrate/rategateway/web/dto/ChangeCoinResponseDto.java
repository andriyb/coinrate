package com.codingchallenge.coinrate.rategateway.web.dto;

import java.util.List;

public class ChangeCoinResponseDto  {


    private CurrentRateFormDto rateForm;
    private List<HistoryRateFormDto> historyRatesForm;

    public ChangeCoinResponseDto(CurrentRateFormDto rateForm, List<HistoryRateFormDto> historyRatesForm) {
        this.rateForm = rateForm;
        this.historyRatesForm = historyRatesForm;
    }

    public CurrentRateFormDto getRateForm() {
        return rateForm;
    }

    public void setRateForm(CurrentRateFormDto rateForm) {
        this.rateForm = rateForm;
    }

    public List<HistoryRateFormDto> getHistoryRatesForm() {
        return historyRatesForm;
    }

    public void setHistoryRatesForm(List<HistoryRateFormDto> historyRatesForm) {
        this.historyRatesForm = historyRatesForm;
    }
}
