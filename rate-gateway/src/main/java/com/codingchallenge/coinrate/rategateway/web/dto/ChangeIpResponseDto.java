package com.codingchallenge.coinrate.rategateway.web.dto;

import java.util.List;

public class ChangeIpResponseDto extends ChangeCoinResponseDto{


    private LocaleFormDto localeForm;

    public ChangeIpResponseDto(CurrentRateFormDto rateForm, List<HistoryRateFormDto> historyRatesForm, LocaleFormDto localeForm) {
        super(rateForm, historyRatesForm);
        this.localeForm = localeForm;
    }

    public LocaleFormDto getLocaleForm() {
        return localeForm;
    }

    public void setLocaleFormDto(LocaleFormDto localeForm) {
        this.localeForm = localeForm;
    }
}
