package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.client.CurrencyServiceClient;
import com.codingchallenge.coinrate.rategateway.client.dto.*;
import com.codingchallenge.coinrate.rategateway.service.dto.form.*;
import com.codingchallenge.coinrate.rategateway.service.mapper.CurrentRateFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.service.mapper.HistoryRateFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.service.mapper.HistorySettingsFormDtoMapper;
import com.codingchallenge.coinrate.rategateway.service.mapper.CoinRateFormDtoMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Service class for handling cryptocurrency rates.
 */
@Service
public class CoinRateService {

    @Value("${rate-gateway.scaled-rate-precision}")
    private Integer scaledRatePrecision = 6;

    @Value("${rate-gateway.scaled-rate-scale}")
    private Integer scaledRateScale = 2;

    @Value("${rate-gateway.current-rate-date-format}")
    private String currentRateDateFormat = "EEEE, dd MMM, yyyy HH:mm:ss";

    @Value("${rate-gateway.history-rate-date-format}")
    private String historyRateDateFormat = "dd MMM, yyyy";

    private static final Logger logger = LogManager.getLogger(CoinRateService.class);

    final CurrencyServiceClient currencyServiceClient;

    @Autowired
    public CoinRateService(CurrencyServiceClient currencyServiceClient) {

        this.currencyServiceClient = currencyServiceClient;
    }

    /**
     * Retrieves the current rate for a specific coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @return The current rate.
     */
    public CurrentRateFormDto getCurrentRate(String coinCode, String currencyCode, Locale locale) {

        CurrentRateFormDto defaultCurrentRateForm = new CurrentRateFormDto();

        // Call the currency service client to get the current rate
        ResponseEntity<CurrentRateDto> responseEntity =
                currencyServiceClient.getCurrentRate(coinCode, currencyCode.toLowerCase());

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            CurrentRateDto currentRateDto = responseEntity.getBody();

            if (currentRateDto != null) {
                return CurrentRateFormDtoMapper.mapClientToFormDto(currentRateDto, LocalDateTime.now(),
                        currentRateDateFormat, scaledRatePrecision, scaledRateScale, locale);
            }
        }
        return defaultCurrentRateForm;
    }

    /**
     * Retrieves the rate history for a specific coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days for the rate history.
     * @return The rate history.
     */
    public List<HistoryRateFormDto> getRateHistory(String coinCode, String currencyCode,
                                                   Integer daysCount, Locale locale) {

        // Call the currency service client to get the rate history
        ResponseEntity<List<HistoryRateDto>> responseEntity =
                currencyServiceClient.getRateHistory(coinCode, currencyCode.toLowerCase(), daysCount);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            List<HistoryRateDto> historyRates = responseEntity.getBody();

            if (historyRates != null) {
                return HistoryRateFormDtoMapper.mapClientToFormDto(historyRates, historyRateDateFormat,
                        scaledRatePrecision, scaledRateScale, locale);
            }
        }
        return List.of();
    }

    /**
     * Retrieves the rate and history for a specific coin and currency, suitable for a form.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days for the rate history.
     * @return The form rate.
     */
    public CoinRateFormDto getFormRate(String coinCode, String currencyCode, Integer daysCount,
                                       List<String> supportedCoins, String ipAddress, LocaleFormDto localeForm,
                                       HistorySettingsFormDto historySettingsForm,
                                       Locale locale) {

        CoinRateFormDto rateForm = new CoinRateFormDto();

        // Call the currency service client to get the form rate
        ResponseEntity<FormRateDto> responseEntity =
                currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {

            FormRateDto formRateDto = responseEntity.getBody();
            if (formRateDto != null) {
                return CoinRateFormDtoMapper.mapClientToFormDto(formRateDto, supportedCoins, coinCode, ipAddress,
                        localeForm, historySettingsForm, LocalDateTime.now(),
                        currentRateDateFormat, historyRateDateFormat,
                        scaledRatePrecision, scaledRateScale, locale);
            } else {
                logger.info("FormRateDto from currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount) " +
                        "is null. CoinCode=" + coinCode + ", currencyCode=" + currencyCode);
            }
        }else {
            logger.info("ResponseEntity from currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount) " +
                    " is null or not OK for CoinCode=" + coinCode + ", currencyCode=" + currencyCode);
        }

        return rateForm;
    }

    /**
     * Retrieves a list of supported coins.
     *
     * @return The list of supported coins.
     */
    public List<String> getSupportedCoins() {

        // Call the currency service client to get the supported coins
        ResponseEntity<List<CoinList>> responseEntity = currencyServiceClient.getSupportedCoins();

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {

            if (responseEntity.getBody() != null) {

                return responseEntity.getBody().stream()
                        .map(CoinList::getCode)
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }

    public HistorySettingsFormDto getHistorySettings() {

        HistorySettingsFormDto defaultHistorySettingsForm =
                new HistorySettingsFormDto(1,1);

        ResponseEntity<HistorySettingsDto> responseEntity = currencyServiceClient.getHistorySettings();

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {

            if (responseEntity.getBody() != null) {
                return HistorySettingsFormDtoMapper.mapClientToFormDto(responseEntity.getBody());
            }
        }
        return defaultHistorySettingsForm;
    }

}
