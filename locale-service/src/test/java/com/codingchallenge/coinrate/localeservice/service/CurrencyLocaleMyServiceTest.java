package com.codingchallenge.coinrate.localeservice.service;

import com.codingchallenge.coinrate.localeservice.domain.CurrencyLocale;
import com.codingchallenge.coinrate.localeservice.repository.CurrencyLocaleRepository;
import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.localeservice.service.mapper.CurrencyLocaleDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyLocaleMyServiceTest {

    @Mock
    private CurrencyLocaleRepository currencyLocaleRepository;

    @Mock
    private ResourceLoader resourceLoader;

    @InjectMocks
    private CurrencyLocaleService currencyLocaleService;

    @Value("${locale-service.default-country-code}")
    private String defaultCountryCode;

    @Value("${locale-service.default-lang-code}")
    private String defaultLangCode;

    @Value("${locale-service.default-currency-code}")
    private String defaultCurrencyCode;

    @Test
    @DisplayName("Should return a list with default currency locale when IP address is invalid")
    void getCurrencyLocalesForInvalidIpAddress() {
        String invalidIp = "invalid_ip_address";
        List<CurrencyLocale> currencyLocales = new ArrayList<>();
        when(currencyLocaleRepository.getByCountryCode(defaultCountryCode)).thenReturn(currencyLocales);
        try {
            List<CurrencyLocaleDto> currencyLocaleDtos = currencyLocaleService.getCurrencyLocales(invalidIp);
            assertEquals(1, currencyLocaleDtos.size());
            CurrencyLocaleDto currencyLocaleDto = currencyLocaleDtos.get(0);
            assertEquals(defaultCountryCode, currencyLocaleDto.getCountryCode());
            assertEquals(defaultLangCode, currencyLocaleDto.getLangCode());
            assertEquals(defaultCurrencyCode, currencyLocaleDto.getCurrencyCode());
            assertTrue(currencyLocaleDto.isDefaultCurrency());
        } catch (Exception e) {
            fail("Should not throw any exception");
        }
    }

    @Test
    @DisplayName("Should return a list with default currency locale when an exception occurs")
    void getCurrencyLocalesWhenExceptionOccurs() {// Mocking the resourceLoader and currencyLocaleRepository
        when(resourceLoader.getResource(anyString())).thenThrow(new RuntimeException());
        when(currencyLocaleRepository.getByCountryCode(anyString())).thenThrow(new RuntimeException());

        // Calling the method under test
        List<CurrencyLocaleDto> currencyLocaleDtos = currencyLocaleService.getCurrencyLocales("127.0.0.1");

        // Asserting the result
        assertEquals(1, currencyLocaleDtos.size());
        CurrencyLocaleDto currencyLocaleDto = currencyLocaleDtos.get(0);
        assertEquals(defaultCountryCode, currencyLocaleDto.getCountryCode());
        assertEquals(defaultLangCode, currencyLocaleDto.getLangCode());
        assertEquals(defaultCurrencyCode, currencyLocaleDto.getCurrencyCode());
        assertTrue(currencyLocaleDto.isDefaultCurrency());

        // Verifying the method calls
        verify(resourceLoader, times(1)).getResource(anyString());
        verify(currencyLocaleRepository, times(1)).getByCountryCode(anyString());
    }

    @Test
    @DisplayName("Should return a list of currency locales for a valid IP address")
    void getCurrencyLocalesForValidIpAddress() {// Mocking the necessary objects and setting up the test data
        String ip = "192.168.0.1";
        List<CurrencyLocale> currencyLocales = new ArrayList<>();
        currencyLocales.add(new CurrencyLocale("US", "en", "USD", true));
        when(currencyLocaleRepository.getByCountryCode(anyString())).thenReturn(currencyLocales);

        Resource resource = mock(Resource.class);
        when(resourceLoader.getResource(anyString())).thenReturn(resource);

        CurrencyLocaleDtoMapper mapper = mock(CurrencyLocaleDtoMapper.class);
        List<CurrencyLocaleDto> expectedCurrencyLocaleDtos = new ArrayList<>();
        expectedCurrencyLocaleDtos.add(new CurrencyLocaleDto("US", "en", "USD", true, false));
        when(mapper.mapEntityToDto(any(CurrencyLocale.class))).thenReturn(new CurrencyLocaleDto("US", "en", "USD", true, false));

        // Calling the method under test
        List<CurrencyLocaleDto> actualCurrencyLocaleDtos = currencyLocaleService.getCurrencyLocales(ip);

        // Asserting the results
        assertEquals(expectedCurrencyLocaleDtos.size(), actualCurrencyLocaleDtos.size());
        assertEquals(expectedCurrencyLocaleDtos.get(0).getCountryCode(), actualCurrencyLocaleDtos.get(0).getCountryCode());
        assertEquals(expectedCurrencyLocaleDtos.get(0).getLangCode(), actualCurrencyLocaleDtos.get(0).getLangCode());
        assertEquals(expectedCurrencyLocaleDtos.get(0).getCurrencyCode(), actualCurrencyLocaleDtos.get(0).getCurrencyCode());
        assertEquals(expectedCurrencyLocaleDtos.get(0).isDefaultCurrency(), actualCurrencyLocaleDtos.get(0).isDefaultCurrency());

        // Verifying the method calls
        verify(currencyLocaleRepository, times(1)).getByCountryCode(anyString());
        verify(resourceLoader, times(1)).getResource(anyString());
        verify(mapper, times(1)).mapEntityToDto(any(CurrencyLocale.class));
    }

}