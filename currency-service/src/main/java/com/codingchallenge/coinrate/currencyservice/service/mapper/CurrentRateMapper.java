package com.codingchallenge.coinrate.currencyservice.service.mapper;

import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import com.codingchallenge.coinrate.currencyservice.service.dto.CurrentRateDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class CurrentRateMapper {

    public static CurrentRateDto toDto(HistoryCoin historyCoin, LocalDateTime currentDateTime) {

        if (historyCoin != null && historyCoin.getMarketData() != null) {

            Map<String, BigDecimal> currentPrice =  historyCoin.getMarketData().getCurrentPrice();

            if (currentPrice != null && currentPrice.size() > 0) {

                Map.Entry<String, BigDecimal> currentPriceMap =
                        currentPrice.entrySet().iterator().next();

                return new CurrentRateDto(
                        historyCoin.getCode(),
                        currentPriceMap.getKey(),
                        historyCoin.getSymbol(),
                        historyCoin.getName(),
                        currentPriceMap.getValue(),
                        currentDateTime);
            }

        }
        return null;
    }
}
