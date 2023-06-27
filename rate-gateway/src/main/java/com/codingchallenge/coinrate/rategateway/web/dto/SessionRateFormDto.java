package com.codingchallenge.coinrate.rategateway.web.dto;

import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.RateDto;

import java.util.List;

public class SessionRateFormDto {

    private List<CurrencyLocaleDto> locale;


    private RateDto rate;

}
