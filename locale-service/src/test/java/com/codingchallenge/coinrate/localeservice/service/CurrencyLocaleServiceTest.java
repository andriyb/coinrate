package com.codingchallenge.coinrate.localeservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codingchallenge.coinrate.localeservice.repository.CurrencyLocaleRepository;
import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CurrencyLocaleService.class})
@ExtendWith(SpringExtension.class)
class CurrencyLocaleServiceTest {
    @MockBean
    private CurrencyLocaleRepository currencyLocaleRepository;

    @Autowired
    private CurrencyLocaleService currencyLocaleService;

    @MockBean
    private ResourceLoader resourceLoader;

    /**
     * Method under test: {@link CurrencyLocaleService#getCurrencyLocales(String)}
     */
    @Test
    void testGetCurrencyLocales() {
        List<CurrencyLocaleDto> actualCurrencyLocales = currencyLocaleService.getCurrencyLocales("en");
        assertEquals(1, actualCurrencyLocales.size());
        CurrencyLocaleDto getResult = actualCurrencyLocales.get(0);
        assertEquals("${locale-service.default-country-code}", getResult.getCountryCode());
        assertTrue(getResult.isDefaultCurrency());
        assertEquals("${locale-service.default-lang-code}", getResult.getLangCode());
        assertEquals("${locale-service.default-currency-code}", getResult.getCurrencyCode());
    }
}

