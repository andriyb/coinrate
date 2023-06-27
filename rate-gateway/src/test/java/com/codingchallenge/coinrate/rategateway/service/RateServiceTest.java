package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.client.CurrencyServiceClient;
import com.codingchallenge.coinrate.rategateway.client.LocaleServiceClient;
import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RateService get locales and valid ip address test")
class RateServiceTest {

    @Mock
    private LocaleServiceClient localeServiceClient;

    @Mock
    private CurrencyServiceClient currencyServiceClient;

    @InjectMocks
    private RateService rateService;

    public static final String INVALID_IP_ADDRESS = "invalidIP";
    public static final String UNKNOWN_VALID_IPV6_ADDRESS = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
    public static final String KNOWN_NL_VALID_IP_ADDRESS = "185.253.96.178";
    public static final String NL_COUNTRY_CODE = "NL";
    public static final String NL_LANG_CODE = "nl";
    public static final String EUR_CURRENCY_CODE = "EUR";
    public static final String GB_COUNTRY_CODE = "GB";
    public static final String GB_LANG_CODE = "en";
    public static final String GBP_CURRENCY_CODE = "GBP";
    public static final String COIN_CODE = "bitcoin";
    public static final String CURRENCY_CODE = "usd";
    public static final Integer DAYS_COUNT = 7;

    public CurrencyLocaleDto defaultCurrencyLocaleDto;

    @BeforeEach
    public void setUp() {

        Properties properties = new Properties();
        try {

            // Load the application-test.yml file from the classpath
            InputStream inputStream = RateServiceTest.class.getClassLoader()
                    .getResourceAsStream("application-test.yml");
            properties.load(inputStream);
            inputStream.close();

            ReflectionTestUtils.setField(rateService, "defaultCountryCode", properties.get("default-country-code"));
            ReflectionTestUtils.setField(rateService, "defaultLangCode", properties.get("default-lang-code"));
            ReflectionTestUtils.setField(rateService, "defaultCurrencyCode", properties.get("default-currency-code"));

            defaultCurrencyLocaleDto = new CurrencyLocaleDto(
                    ReflectionTestUtils.getField(rateService, "defaultCountryCode").toString(),
                    ReflectionTestUtils.getField(rateService, "defaultLangCode").toString(),
                    ReflectionTestUtils.getField(rateService, "defaultCurrencyCode").toString(),
                    true, false);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    @DisplayName("Should return currency locales from LocaleServiceClient when IP address is valid and known")
    void getLocalesWhenKnownIpAddressIsValid() {

        // Create a list of expected currency locales for a known IP address
        List<CurrencyLocaleDto> expectedCurrencyLocales = new ArrayList<>();
        expectedCurrencyLocales.add(
                new CurrencyLocaleDto(NL_COUNTRY_CODE, NL_LANG_CODE, EUR_CURRENCY_CODE,
                        true, false));
        expectedCurrencyLocales.add(
                new CurrencyLocaleDto(GB_COUNTRY_CODE, GB_LANG_CODE, GBP_CURRENCY_CODE,
                        false, false));

        // Mock the response from the LocaleServiceClient for a known IP address
        when(localeServiceClient.getCurrencyLocales(KNOWN_NL_VALID_IP_ADDRESS)).thenReturn(
                new ResponseEntity<>(expectedCurrencyLocales, HttpStatus.OK));

        // Call the getLocales method of the RateService for a known IP address
        List<CurrencyLocaleDto> actualCurrencyLocales = rateService.getLocales(KNOWN_NL_VALID_IP_ADDRESS);

        // Verify that the returned currency locales match the expected values
        assertEquals(expectedCurrencyLocales.size(), actualCurrencyLocales.size());
        assertEquals(expectedCurrencyLocales.get(0).getCountryCode(), actualCurrencyLocales.get(0).getCountryCode());
        assertEquals(expectedCurrencyLocales.get(0).getLangCode(), actualCurrencyLocales.get(0).getLangCode());
        assertEquals(expectedCurrencyLocales.get(0).getCurrencyCode(), actualCurrencyLocales.get(0).getCurrencyCode());
        assertEquals(expectedCurrencyLocales.get(0).isDefaultCurrency(), actualCurrencyLocales.get(0).isDefaultCurrency());
        assertEquals(expectedCurrencyLocales.get(1).getCountryCode(), actualCurrencyLocales.get(1).getCountryCode());
        assertEquals(expectedCurrencyLocales.get(1).getLangCode(), actualCurrencyLocales.get(1).getLangCode());
        assertEquals(expectedCurrencyLocales.get(1).getCurrencyCode(), actualCurrencyLocales.get(1).getCurrencyCode());
        assertEquals(expectedCurrencyLocales.get(1).isDefaultCurrency(), actualCurrencyLocales.get(1).isDefaultCurrency());

        // Verify that the LocaleServiceClient was called exactly once with the correct IP address
        verify(localeServiceClient, times(1)).getCurrencyLocales(KNOWN_NL_VALID_IP_ADDRESS);

    }

    @Test
    @DisplayName("Should return default locales from LocaleServiceClient when IP address is valid and unknown")
    void getLocalesWhenUnknownIpAddressIsValid() {

        // Create a list of expected currency locales for an unknown IP address
        List<CurrencyLocaleDto> expectedCurrencyLocales = new ArrayList<>();
        expectedCurrencyLocales.add(defaultCurrencyLocaleDto);

        // Mock the response from the LocaleServiceClient for an unknown IP address
        when(localeServiceClient.getCurrencyLocales(UNKNOWN_VALID_IPV6_ADDRESS)).thenReturn(
                new ResponseEntity<>(expectedCurrencyLocales, HttpStatus.OK));

        // Call the getLocales method of the RateService for an unknown IP address
        List<CurrencyLocaleDto> actualCurrencyLocales = rateService.getLocales(UNKNOWN_VALID_IPV6_ADDRESS);

        // Verify that the returned currency locales match the expected values
        assertEquals(expectedCurrencyLocales.size(), actualCurrencyLocales.size());
        assertEquals(expectedCurrencyLocales.get(0).getCountryCode(), actualCurrencyLocales.get(0).getCountryCode());
        assertEquals(expectedCurrencyLocales.get(0).getLangCode(), actualCurrencyLocales.get(0).getLangCode());
        assertEquals(expectedCurrencyLocales.get(0).getCurrencyCode(), actualCurrencyLocales.get(0).getCurrencyCode());
        assertEquals(expectedCurrencyLocales.get(0).isDefaultCurrency(), actualCurrencyLocales.get(0).isDefaultCurrency());

        // Verify that the LocaleServiceClient was called exactly once with the correct IP address
        verify(localeServiceClient, times(1)).getCurrencyLocales(UNKNOWN_VALID_IPV6_ADDRESS);

    }

    @Test
    @DisplayName("Should return default locale when IP address is invalid")
    void getLocalesWhenIpAddressIsInvalid() {

        // Create a list of expected currency locales with the default values
        List<CurrencyLocaleDto> expectedCurrencyLocales = new ArrayList<>();
        expectedCurrencyLocales.add(defaultCurrencyLocaleDto);

        // Call the getLocales method of the RateService with an invalid IP address
        List<CurrencyLocaleDto> actualCurrencyLocales = rateService.getLocales(INVALID_IP_ADDRESS);

        // Verify that the returned currency locales match the expected values
        assertEquals(expectedCurrencyLocales.size(), actualCurrencyLocales.size());
        assertEquals(expectedCurrencyLocales.get(0).getCountryCode(), actualCurrencyLocales.get(0).getCountryCode());
        assertEquals(expectedCurrencyLocales.get(0).getLangCode(), actualCurrencyLocales.get(0).getLangCode());
        assertEquals(expectedCurrencyLocales.get(0).getCurrencyCode(), actualCurrencyLocales.get(0).getCurrencyCode());
        assertEquals(expectedCurrencyLocales.get(0).isDefaultCurrency(), actualCurrencyLocales.get(0).isDefaultCurrency());

        // Verify that the LocaleServiceClient was not called
        verifyNoInteractions(localeServiceClient);

    }

    @Test
    @DisplayName("Should return default locale when IP address null")
    void getLocalesWhenIpAddressNull() {

        // Create a list of expected currency locales with the default values
        List<CurrencyLocaleDto> expectedCurrencyLocales = new ArrayList<>();
        expectedCurrencyLocales.add(defaultCurrencyLocaleDto);

        // Call the getLocales method of the RateService with a null IP address
        List<CurrencyLocaleDto> actualCurrencyLocales = rateService.getLocales(null);

        // Verify that the returned currency locales match the expected values
        assertEquals(expectedCurrencyLocales.size(), actualCurrencyLocales.size());
        assertEquals(expectedCurrencyLocales.get(0).getCountryCode(), actualCurrencyLocales.get(0).getCountryCode());
        assertEquals(expectedCurrencyLocales.get(0).getLangCode(), actualCurrencyLocales.get(0).getLangCode());
        assertEquals(expectedCurrencyLocales.get(0).getCurrencyCode(), actualCurrencyLocales.get(0).getCurrencyCode());
        assertEquals(expectedCurrencyLocales.get(0).isDefaultCurrency(), actualCurrencyLocales.get(0).isDefaultCurrency());

        // Verify that the LocaleServiceClient was not called
        verifyNoInteractions(localeServiceClient);

    }

    @Test
    @DisplayName("Should return false when ipAddress is not a valid IPv4 or IPv6 address")
    void isValidIPAddressWhenIpAddressIsNotValid() {

        // Execute the method being tested, passing in the invalid IP address
        boolean result = ReflectionTestUtils.invokeMethod(rateService, "isValidIPAddress", INVALID_IP_ADDRESS);

        // Verify that the result is false, since the IP address is not valid
        assertFalse(result);

    }

    @Test
    @DisplayName("Should return true when ipAddress is a valid IPv6 address")
    void isValidIPAddressWhenIpAddressIsValidIPv6() {

        // Execute the method being tested, passing in the valid IPv6 address
        boolean result = ReflectionTestUtils.invokeMethod(rateService, "isValidIPAddress", UNKNOWN_VALID_IPV6_ADDRESS);

        // Verify that the result is true, since the IP address is valid
        assertTrue(result);

    }

    @Test
    @DisplayName("Should return true when ipAddress is a valid IPv4 address")
    void isValidIPAddressWhenIpAddressIsValidIPv4() {

        // Execute the method being tested, passing in the valid IPv4 address
        boolean result = ReflectionTestUtils.invokeMethod(rateService, "isValidIPAddress", KNOWN_NL_VALID_IP_ADDRESS);

        // Verify that the result is true, since the IP address is valid
        assertTrue(result);

    }
}