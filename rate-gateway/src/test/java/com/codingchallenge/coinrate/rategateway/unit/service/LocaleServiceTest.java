package com.codingchallenge.coinrate.rategateway.unit.service;
import com.codingchallenge.coinrate.rategateway.client.LocaleServiceClient;
import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.LocaleService;
import com.codingchallenge.coinrate.rategateway.service.dto.form.LocaleFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.LocaleFormDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@DisplayName("LocaleService tests")
public class LocaleServiceTest {

    @Mock
    private LocaleServiceClient localeServiceClient;

    private LocaleService localeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        localeService = new LocaleService(localeServiceClient);
    }


    @Test
    @DisplayName("Get Default Currency Locale - Valid IP Address")
    public void testGetDefaultCurrencyLocale_ValidIpAddress() {
        // Mocked IP address
        String ipAddress = "85.203.15.10";

        // Mocked CurrencyLocaleDto
        CurrencyLocaleDto currencyLocaleDto = new CurrencyLocaleDto("DE", "de", "EUR", true, true);

        // Mocked ResponseEntity with the CurrencyLocaleDto
        ResponseEntity<List<CurrencyLocaleDto>> responseEntity = new ResponseEntity<>(
                Arrays.asList(currencyLocaleDto), HttpStatus.OK);

        // Mock the behavior of the localeServiceClient
        Mockito.when(localeServiceClient.getCurrencyLocales(ipAddress)).thenReturn(responseEntity);

        // Create the expected LocaleFormDto based on the mocked data
        Locale expectedLocale = new Locale(currencyLocaleDto.getLangCode().toLowerCase(),
                currencyLocaleDto.getCountryCode().toUpperCase());
        LocaleFormDto expectedLocaleForm = LocaleFormDtoMapper.mapClientToFormDto(currencyLocaleDto, expectedLocale);

        // Call the method under test
        LocaleFormDto result = localeService.getDefaultCurrencyLocale(ipAddress);

        // Assertions
        assertEquals(expectedLocaleForm, result);
        Mockito.verify(localeServiceClient, Mockito.times(1))
                .getCurrencyLocales(eq(ipAddress));
    }

    @Test
    @DisplayName("Get Default Currency Locale - Invalid IP Address")
    public void testGetDefaultCurrencyLocale_InvalidIpAddress() {
        // Mocked invalid IP address
        String ipAddress = "invalid_ip_address";

        // Mocked default LocaleFormDto
        LocaleFormDto defaultLocaleForm = new LocaleFormDto("Germany", "DE", "German", "de", "Euro", "EUR",
                true, true);

        // Create an instance of LocaleService with the mocked localeServiceClient
        LocaleService localeService = new LocaleService(localeServiceClient);

        // Call the method under test
        LocaleFormDto result = localeService.getDefaultCurrencyLocale(ipAddress);

        // Assertions
        assertEquals(defaultLocaleForm, result);
        Mockito.verify(localeServiceClient, Mockito.never())
                .getCurrencyLocales(any());
    }

    @Test
    @DisplayName("Get Default Currency Locale - Empty Currency Locales List")
    public void testGetDefaultCurrencyLocale_EmptyCurrencyLocalesList() {
        // Mocked IP address
        String ipAddress = "85.203.15.10";

        // Mocked empty currency locales list
        List<CurrencyLocaleDto> currencyLocaleList = new ArrayList<>();

        // Mocked ResponseEntity with the empty currency locales list
        ResponseEntity<List<CurrencyLocaleDto>> responseEntity = new ResponseEntity<>(
                currencyLocaleList, HttpStatus.OK);

        // Mock the behavior of the localeServiceClient
        Mockito.when(localeServiceClient.getCurrencyLocales(ipAddress)).thenReturn(responseEntity);

        // Mocked default LocaleFormDto
        LocaleFormDto defaultLocaleForm = new LocaleFormDto("Germany", "DE", "German", "de", "Euro", "EUR",
                true, true);

        // Create an instance of LocaleService with the mocked localeServiceClient
        LocaleService localeService = new LocaleService(localeServiceClient);

        // Call the method under test
        LocaleFormDto result = localeService.getDefaultCurrencyLocale(ipAddress);

        // Assertions
        assertEquals(defaultLocaleForm, result);
        Mockito.verify(localeServiceClient, Mockito.times(1))
                .getCurrencyLocales(eq(ipAddress));
    }

    @Test
    @DisplayName("Get Default Currency Locale - Invalid Response from Locale Service Client")
    public void testGetDefaultCurrencyLocale_InvalidResponseFromLocaleServiceClient() {
        // Mocked IP address
        String ipAddress = "85.203.15.10";

        // Mocked ResponseEntity with null body
        ResponseEntity<List<CurrencyLocaleDto>> responseEntity = new ResponseEntity<>(null, HttpStatus.OK);

        // Mock the behavior of the localeServiceClient
        Mockito.when(localeServiceClient.getCurrencyLocales(ipAddress)).thenReturn(responseEntity);

        // Mocked default LocaleFormDto
        LocaleFormDto defaultLocaleForm = new LocaleFormDto("Germany", "DE", "German", "de", "Euro", "EUR",
                true, true);

        // Create an instance of LocaleService with the mocked localeServiceClient
        LocaleService localeService = new LocaleService(localeServiceClient);

        // Call the method under test
        LocaleFormDto result = localeService.getDefaultCurrencyLocale(ipAddress);

        // Assertions
        assertEquals(defaultLocaleForm, result);
        Mockito.verify(localeServiceClient, Mockito.times(1))
                .getCurrencyLocales(eq(ipAddress));
    }

}