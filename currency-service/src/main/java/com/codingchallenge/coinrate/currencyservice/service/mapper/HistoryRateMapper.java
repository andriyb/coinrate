package com.codingchallenge.coinrate.currencyservice.service.mapper;

import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import com.codingchallenge.coinrate.currencyservice.domain.RateHistory;
import com.codingchallenge.coinrate.currencyservice.service.dto.HistoryRateDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryRateMapper {

    public static HistoryRateDto toDto(RateHistory entity) {

        return new HistoryRateDto(
                entity.getCoinCode(),
                entity.getCurrencyCode(),
                entity.getCoinName(),
                entity.getCoinSymbol(),
                entity.getRate(),
                entity.getDate()
        );
    }

    public static List<RateHistory> toEntityList(HistoryCoin coinHistory, LocalDate currentDate) {

        if (coinHistory != null) {
            List<RateHistory> rateHistoryList = new ArrayList<>();
            coinHistory.getMarketData().getCurrentPrice().forEach((key, value) -> {
                RateHistory rateHistory = new RateHistory();
                rateHistory.setDate(currentDate);
                rateHistory.setCurrencyCode(key);
                rateHistory.setCoinCode(coinHistory.getCode());
                rateHistory.setCoinSymbol(coinHistory.getSymbol());
                rateHistory.setCoinName(coinHistory.getName());
                rateHistory.setRate(value);
                rateHistoryList.add(rateHistory);
            });
            return rateHistoryList;
        } else {
            return Collections.emptyList();
        }

    }

    public static List<HistoryRateDto> toDtoList(List<RateHistory> entities) {
        return entities.stream()
                .map(HistoryRateMapper::toDto)
                .collect(Collectors.toList());
    }
}
