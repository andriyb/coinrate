package com.codingchallenge.coinrate.rategateway.service.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.FormRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 * Mapper class for converting cryptocurrency data between the microservice and form data transfer objects.
 */
public class CoinRateFormDtoMapper {

    /**
     * Maps the microservice data to the CoinRateFormDto object.
     *
     * @param formRate             The form rate data.
     * @param supportedCoins       The list of supported coin codes.
     * @param selectedCoin         The selected coin code.
     * @param ipAddress            The IP address.
     * @param localeForm           The locale form data.
     * @param historySettings      The history settings.
     * @param requestTime           The current rate requestTime.
     * @param currentRateDateFormat  The date format for the current rate.
     * @param historyRateDateFormat  The date format for the history rate.
     * @param desiredPrecision     The desired precision for scaling the rates.
     * @param desiredScale         The desired scale for scaling the rates.
     * @param locale               The locale.
     * @return The mapped CoinRateFormDto object.
     */
    public static CoinRateFormDto mapClientToFormDto(FormRateDto formRate, List<String> supportedCoins,
                                                     String selectedCoin, String ipAddress,
                                                     LocaleFormDto localeForm, HistorySettingsFormDto historySettings,
                                                     LocalDateTime requestTime,
                                                     String currentRateDateFormat, String historyRateDateFormat,
                                                     int desiredPrecision, int desiredScale, Locale locale) {

        // Map the current rate data
        CurrentRateFormDto currentRateForm = CurrentRateFormDtoMapper.mapClientToFormDto(formRate,
                requestTime,currentRateDateFormat, desiredPrecision, desiredScale, locale);

        // Map the history rate data
        List<HistoryRateFormDto> historyRateForm  =
                HistoryRateFormDtoMapper.mapClientToFormDto(formRate.getHistory(), historyRateDateFormat,
                        desiredPrecision, desiredScale,locale);

        return new CoinRateFormDto(supportedCoins, selectedCoin, ipAddress, localeForm, currentRateForm,
                historyRateForm,  historySettings);

    }
}