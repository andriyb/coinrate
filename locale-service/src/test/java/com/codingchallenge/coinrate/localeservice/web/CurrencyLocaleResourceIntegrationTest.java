package com.codingchallenge.coinrate.localeservice.web;

import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("CurrencyLocaleResource integration test")
class CurrencyLocaleResourceIntegrationTest {

    public static final String REST_API_ENDPOINT = "/api/v1/getCurrencyLocales";
    public static final String KNOWN_NL_IP_ADDRESS = "185.253.96.178";
    public static final String NL_COUNTRY_CODE = "NL";
    public static final String NL_LANG_CODE = "nl";
    public static final String EUR_CURRENCY_CODE = "EUR";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should return the correct currency locales for a known IP address in the Netherlands")
    void testGetCurrencyLocales() {
        // Prepare the request URL with query parameters
        String url = REST_API_ENDPOINT + "?ip=" + KNOWN_NL_IP_ADDRESS;

        // Make the HTTP GET request
        ResponseEntity<List<CurrencyLocaleDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CurrencyLocaleDto>>() {}
        );

        // Assert the response status code
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert the response body or perform other validations as needed
        List<CurrencyLocaleDto> currencyLocales = response.getBody();
        assertNotNull(currencyLocales);
        assertEquals(NL_COUNTRY_CODE, currencyLocales.get(0).getCountryCode());
        assertEquals(NL_LANG_CODE, currencyLocales.get(0).getLangCode());
        assertEquals(EUR_CURRENCY_CODE, currencyLocales.get(0).getCurrencyCode());
    }
}