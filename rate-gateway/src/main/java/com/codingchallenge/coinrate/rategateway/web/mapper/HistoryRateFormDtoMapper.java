package com.codingchallenge.coinrate.rategateway.web.mapper;

import com.codingchallenge.coinrate.rategateway.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.web.dto.HistoryRateFormDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.codingchallenge.coinrate.rategateway.web.mapper.CurrentRateFormDtoMapper.calcScaleShift;

public class HistoryRateFormDtoMapper {

    public static List<HistoryRateFormDto> mapClientToFormDto(List<HistoryRateDto> historyRates, Locale locale) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale);

        int desiredPrecision = 6;
        int desiredScale = 2;


        List<HistoryRateFormDto> historyRateForm  = historyRates.stream()
                .map(historyRate -> {
                    String formattedDate = dateFormatter.format(historyRate.getRateDate());

                    BigDecimal rate = historyRate.getCurrentRate();

                    Integer calcScaleShift = calcScaleShift(rate, desiredPrecision, desiredScale);
                    BigDecimal result = rate.multiply(BigDecimal.TEN.pow(calcScaleShift));
                    String displayRate = ((int) Math.pow(10, calcScaleShift)) + " " + historyRate.getCoinSymbol() + " = "
                            +currencyFormat.format(result.setScale(desiredScale, RoundingMode.HALF_UP));


                    return new HistoryRateFormDto(formattedDate, displayRate);
                })
                .collect(Collectors.toList());

        return historyRateForm;
    }
}
