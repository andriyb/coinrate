package com.codingchallenge.coinrate.currencyservice.service.mapper;

import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import com.codingchallenge.coinrate.currencyservice.service.dto.CurrentRateDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class CurrentRateMapper {

    public static CurrentRateDto toDto(HistoryCoin historyCoin, String currencyCode, LocalDateTime currentDateTime) {

        if (historyCoin != null && historyCoin.getMarketData() != null) {
            Map<String, BigDecimal> currentPrice =  historyCoin.getMarketData().getCurrentPrice();
            if (currentPrice != null && currentPrice.size() > 0) {
                BigDecimal rateValue = currentPrice.get(currencyCode);
                return new CurrentRateDto(
                        historyCoin.getCode(),
                        currencyCode,
                        historyCoin.getSymbol(),
                        historyCoin.getName(),
                        rateValue,
                        currentDateTime);
            }
        }
        return null;
    }
}
