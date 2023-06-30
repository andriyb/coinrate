package com.codingchallenge.coinrate.rategateway.unit.mapper;
import com.codingchallenge.coinrate.rategateway.client.dto.CurrentRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.CurrentRateFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.CurrentRateFormDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Locale;

@DisplayName("CurrentRateFormDtoMapper tests")
class CurrentRateFormDtoMapperTest {

    @Test
    @DisplayName("Mapping CurrentRateDto to CurrentRateFormDto with rate DateTime")
    void testMapClientToFormDto_WithRateDateTime() {
        // Mock data for testing
        CurrentRateDto currentRateDto = new CurrentRateDto();
        currentRateDto.setCurrentRate(new BigDecimal("123.456"));
        currentRateDto.setCoinSymbol("btc");
        currentRateDto.setRateDateTime(LocalDateTime.parse("2023-06-27T09:30:00"));
        String currentRateDateFormat = "yyyy-MM-dd HH:mm:ss";
        int desiredPrecision = 6;
        int desiredScale = 2;
        Locale locale = Locale.GERMANY;

        // Perform the mapping
        CurrentRateFormDto result = CurrentRateFormDtoMapper.mapClientToFormDto(currentRateDto,
                currentRateDateFormat, desiredPrecision, desiredScale, locale);

        // Assert the mapping result
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedRate = "10 btc = " + currencyFormat.format(new BigDecimal("1234.56"));
        Assertions.assertEquals(formattedRate, result.getFormattedRate());
        Assertions.assertEquals("2023-06-27 09:30:00", result.getFormattedUpdateDateTime());
    }

    @Test
    @DisplayName("Mapping CurrentRateDto to CurrentRateFormDto without rate DateTime")
    void testMapClientToFormDto_WithoutRateDateTime() {
        // Mock data for testing
        CurrentRateDto currentRateDto = new CurrentRateDto();
        currentRateDto.setCurrentRate(new BigDecimal("789.012"));
        currentRateDto.setCoinSymbol("btc");
        String currentRateDateFormat = "yyyy-MM-dd HH:mm:ss";
        int desiredPrecision = 6;
        int desiredScale = 2;
        Locale locale = Locale.GERMANY;

        // Perform the mapping
        CurrentRateFormDto result = CurrentRateFormDtoMapper.mapClientToFormDto(currentRateDto,
                currentRateDateFormat, desiredPrecision, desiredScale, locale);

        // Assert the mapping result
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedRate = "10 btc = " + currencyFormat.format(new BigDecimal("7890.12"));
        Assertions.assertEquals(formattedRate, result.getFormattedRate());
        Assertions.assertEquals("", result.getFormattedUpdateDateTime());
    }
}