package com.codingchallenge.coinrate.rategateway.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.FormRateDto;
import com.codingchallenge.coinrate.rategateway.client.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.*;
import com.codingchallenge.coinrate.rategateway.service.mapper.CoinRateFormDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@DisplayName("CoinRateFormDtoMapper tests")
class CoinRateFormDtoMapperTest {

    @Test
    @DisplayName("Mapping FormRateDto to CoinRateFormDto with history rates")
    void testMapClientToFormDto_WithHistoryRates() {
        // Mock data for testing
        FormRateDto formRateDto = new FormRateDto();
        formRateDto.setCurrentRate(new BigDecimal("27933.12"));
        formRateDto.setHistory(Arrays.asList(
                new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                        new BigDecimal("27974.166955638662"), LocalDate.parse("2023-06-25")) ,

                new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                        new BigDecimal("0.000027933"), LocalDate.parse("2023-06-26")) ,

                new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                        new BigDecimal("0.27933"), LocalDate.parse("2023-06-25") )
        ));
        List<String> supportedCoins = Arrays.asList("bitcoin", "ethereum", "action-coin");
        String selectedCoin = "bitcoin";
        String ipAddress = "127.0.0.1";
        LocaleFormDto localeForm = new LocaleFormDto("Germany", "DE", "German", "de", "Euro", "EUR", true, false);
        HistorySettingsFormDto historySettings = new HistorySettingsFormDto(30, 7);
        String currentRateDateFormat = "yyyy-MM-dd HH:mm:ss";
        String historyRateDateFormat = "yyyy-MM-dd";
        int desiredPrecision = 6;
        int desiredScale = 2;
        Locale locale = Locale.GERMANY;

        // Perform the mapping
        CoinRateFormDto result = CoinRateFormDtoMapper.mapClientToFormDto(formRateDto, supportedCoins, selectedCoin,
                ipAddress, localeForm, historySettings, currentRateDateFormat, historyRateDateFormat,
                desiredPrecision, desiredScale, locale);

        // Assert the mapping result
        Assertions.assertEquals(supportedCoins, result.getSupportedCoins());
        Assertions.assertEquals(selectedCoin, result.getSelectedCoin());
        Assertions.assertEquals(ipAddress, result.getIpAddress());
        Assertions.assertEquals(localeForm, result.getLocaleForm());
        Assertions.assertNotNull(result.getCurrentRateForm());
        Assertions.assertNotNull(result.getHistoryRateForms());
        Assertions.assertEquals(3, result.getHistoryRateForms().size());

        // Assert the current rate
        CurrentRateFormDto currentRateForm = result.getCurrentRateForm();
        Assertions.assertNotNull(currentRateForm.getDisplayRate());
        Assertions.assertNotNull(currentRateForm.getDisplayUpdateDateTime());

        // Assert the history rates
        List<HistoryRateFormDto> historyRateForm = result.getHistoryRateForms();
        Assertions.assertEquals(3, historyRateForm.size());
        for (HistoryRateFormDto historyRate : historyRateForm) {
            Assertions.assertNotNull(historyRate.getDisplayRate());
            Assertions.assertNotNull(historyRate.getDisplayRateDate());
        }
    }

    @Test
    @DisplayName("Mapping FormRateDto to CoinRateFormDto without history rates")
    void testMapClientToFormDto_WithoutHistoryRates() {
        // Mock data for testing
        FormRateDto formRateDto = new FormRateDto();
        formRateDto.setCurrentRate(new BigDecimal("27933.12"));
        formRateDto.setHistory(List.of());
        List<String> supportedCoins = Arrays.asList("bitcoin", "ethereum", "action-coin");
        String selectedCoin = "bitcoin";
        String ipAddress = "127.0.0.1";
        LocaleFormDto localeForm = new LocaleFormDto("Germany", "DE", "German", "de", "Euro", "EUR", true, false);
        HistorySettingsFormDto historySettings = new HistorySettingsFormDto(7, 5);
        String currentRateDateFormat = "yyyy-MM-dd HH:mm:ss";
        String historyRateDateFormat = "yyyy-MM-dd";
        int desiredPrecision = 6;
        int desiredScale = 2;
        Locale locale = Locale.GERMANY;

        // Perform the mapping
        CoinRateFormDto result = CoinRateFormDtoMapper.mapClientToFormDto(formRateDto, supportedCoins, selectedCoin,
                ipAddress, localeForm, historySettings, currentRateDateFormat, historyRateDateFormat,
                desiredPrecision, desiredScale, locale);

        // Assert the mapping result
        Assertions.assertEquals(supportedCoins, result.getSupportedCoins());
        Assertions.assertEquals(selectedCoin, result.getSelectedCoin());
        Assertions.assertEquals(ipAddress, result.getIpAddress());
        Assertions.assertEquals(localeForm, result.getLocaleForm());
        Assertions.assertNotNull(result.getCurrentRateForm());
        Assertions.assertTrue(result.getHistoryRateForms().isEmpty());

        // Assert the current rate
        CurrentRateFormDto currentRateForm = result.getCurrentRateForm();
        Assertions.assertNotNull(currentRateForm.getDisplayRate());
        Assertions.assertNotNull(currentRateForm.getDisplayUpdateDateTime());
    }
}