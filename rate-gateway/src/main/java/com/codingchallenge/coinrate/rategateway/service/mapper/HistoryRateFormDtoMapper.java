package com.codingchallenge.coinrate.rategateway.service.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.HistoryRateFormDto;
import com.codingchallenge.coinrate.rategateway.service.util.RateScaleUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Mapper class for converting cryptocurrency history rates data between the microservice and form data
 * transfer objects.
 */
public class HistoryRateFormDtoMapper {
    /**
     * Maps a list of client history rates to a list of HistoryRateFormDto objects.
     *
     * @param historyRates           The list of client history rates.
     * @param currentRateDateFormat  The date format for the history rates.
     * @param desiredPrecision       The desired precision for scaling the rates.
     * @param desiredScale           The desired scale for scaling the rates.
     * @param locale                 The locale.
     * @return The list of mapped HistoryRateFormDto objects.
     */
    public static List<HistoryRateFormDto> mapClientToFormDto(List<HistoryRateDto> historyRates,
                                                              String currentRateDateFormat,
                                                              int desiredPrecision, int desiredScale, Locale locale) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(currentRateDateFormat).withLocale(locale);

        return historyRates.stream()
                .map(historyRate -> {
                    String formattedDate = dateFormatter.format(historyRate.getRateDate());

                    // Get the scaled rate with desired precision and scale using RateScaleUtil
                    BigDecimal rate = historyRate.getCurrentRate();
                    String displayRate = RateScaleUtil.getScaledRate(rate, desiredPrecision, desiredScale,
                            historyRate.getCoinSymbol(), currencyFormat);

                    return new HistoryRateFormDto(displayRate, formattedDate);
                })
                .collect(Collectors.toList());
    }
}
