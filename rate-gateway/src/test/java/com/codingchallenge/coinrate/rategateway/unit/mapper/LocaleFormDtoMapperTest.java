package com.codingchallenge.coinrate.rategateway.unit.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.LocaleFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.LocaleFormDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LocaleFormDtoMapper tests")
class LocaleFormDtoMapperTest {

    @Test
    @DisplayName("Mapping client currency locale to form DTO with German format settings")
    void testMapClientToFormDto_GermanFormatSettings() {
        // Prepare test data
        CurrencyLocaleDto currencyLocaleDto = new CurrencyLocaleDto("DE", "de", "EUR", true, false);
        Locale currentLocale = new Locale("de", "DE");

        // Perform the mapping
        LocaleFormDto formDto = LocaleFormDtoMapper.mapClientToFormDto(currencyLocaleDto, currentLocale);

        // Verify the mapping results
        assertEquals("Germany", formDto.getCountry());
        assertEquals("DE", formDto.getCountryCode());
        assertEquals("German", formDto.getLanguage());
        assertEquals("de", formDto.getLangCode());
        assertEquals("Euro", formDto.getCurrency());
        assertEquals("EUR", formDto.getCurrencyCode());
        assertTrue(formDto.isDefault());
        assertFalse(formDto.isLocaleNotFound());
    }
}
