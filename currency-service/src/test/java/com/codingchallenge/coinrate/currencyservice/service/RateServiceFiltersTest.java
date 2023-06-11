package com.codingchallenge.coinrate.currencyservice.service;

import com.codingchallenge.coinrate.currencyservice.client.GeckoApiClient;
import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("RateService currency and coin filters tests")
@ActiveProfiles("test")
class RateServiceFiltersTest {

    @Mock
    private GeckoApiClient geckoApiClient;

    @InjectMocks
    private RateService rateService;


    @Test
    @DisplayName("Should return an empty set when the API response is not ok")
    void applyCoinFilterWhenApiResponseIsNotOk() {// Set up
        Set<String> coinFilter = new HashSet<>(Arrays.asList("bitcoin", "ethereum"));
        ResponseEntity<List<CoinList>> responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        when(geckoApiClient.getCoinList()).thenReturn(responseEntity);

        // Execute
        Set<CoinList> result = ReflectionTestUtils.invokeMethod(rateService, "applyCoinFilter", coinFilter);

        // Verify
        assertTrue(result.isEmpty());
        verify(geckoApiClient, times(1)).getCoinList();
    }

    @Test
    @DisplayName("Should return an empty set when the coin filter is empty")
    void applyCoinFilterWhenCoinFilterIsEmpty() {
        Set<String> coinFilter = new HashSet<>();
        List<CoinList> coinsAvailable = Arrays.asList(
                new CoinList("bitcoin", "btc", "Bitcoin"),
                new CoinList("ethereum", "eth", "Ethereum"),
                new CoinList("litecoin", "ltc","Litecoin")
        );

        ResponseEntity<List<CoinList>> supportedCoinsResponse = new ResponseEntity<>(coinsAvailable, HttpStatus.OK);
        when(geckoApiClient.getCoinList()).thenReturn(supportedCoinsResponse);

        // Execute filter
        Set<CoinList> result = ReflectionTestUtils.invokeMethod(rateService, "applyCoinFilter", coinFilter);

        // Verify
        assertTrue(result.isEmpty());
        verify(geckoApiClient, times(1)).getCoinList();
    }

    @Test
    @DisplayName("Should return a set of filtered coins when the coin filter is applied")
    void applyCoinFilterWhenCoinFilterIsApplied() {
        Set<String> coinFilter = new HashSet<>(Arrays.asList("bitcoin", "ethereum", "litecoin"));
        List<CoinList> coinsAvailable = Arrays.asList(
                new CoinList("bitcoin", "btc","Bitcoin"),
                new CoinList("ethereum", "eth","Ethereum"),
                new CoinList("litecoin", "ltc","Litecoin"),
                new CoinList("ripple", "xrp", "XRP")
        );
        ResponseEntity<List<CoinList>> supportedCoinsResponse = new ResponseEntity<>(coinsAvailable, HttpStatus.OK);
        when(geckoApiClient.getCoinList()).thenReturn(supportedCoinsResponse);

        // Execute filter
        Set<CoinList> result = ReflectionTestUtils.invokeMethod(rateService, "applyCoinFilter", coinFilter);

        // Verify
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(c -> c.getCode().equals("bitcoin")));
        assertTrue(result.stream().anyMatch(c -> c.getCode().equals("ethereum")));
        assertTrue(result.stream().anyMatch(c -> c.getCode().equals("litecoin")));
        verify(geckoApiClient, times(1)).getCoinList();
    }

    @Test
    @DisplayName("Should return an empty set when supported currencies response status is not ok")
    void applyCurrencyFilterWhenResponseStatusNotOk() {// Set up
        ResponseEntity<List<String>> supportedCurrencyResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(supportedCurrencyResponse);

        // Execute filter
        Set<String> result = ReflectionTestUtils.invokeMethod(rateService, "applyCurrencyFilter", new HashSet<>());

        // Verify
        assertTrue(result.isEmpty());
        verify(geckoApiClient, times(1)).getSupportedCurrencies();
    }

    @Test
    @DisplayName("Should return an empty set when supported currencies are not available")
    void applyCurrencyFilterWhenSupportedCurrenciesNotAvailable() {// Set up
        ResponseEntity<List<String>> supportedCurrencyResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(supportedCurrencyResponse);

        // Execute filter
        Set<String> currencyFilter = new HashSet<>(Arrays.asList("USD", "EUR", "GBP"));
        Set<String> result = ReflectionTestUtils.invokeMethod(rateService, "applyCurrencyFilter", currencyFilter);

        // Verify
        assertTrue(result.isEmpty());
        verify(geckoApiClient, times(1)).getSupportedCurrencies();
    }

    @Test
    @DisplayName("Should return a set of filtered currencies when supported currencies are available")
    void applyCurrencyFilterWhenSupportedCurrenciesAvailable() {
        List<String> supportedCurrencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "CAD");
        Set<String> currencyFilter = new HashSet<>(Arrays.asList("USD", "EUR", "JPY"));
        ResponseEntity<List<String>> supportedCurrencyResponse = new ResponseEntity<>(supportedCurrencies, HttpStatus.OK);
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(supportedCurrencyResponse);

        // Execute filter
        Set<String> result = ReflectionTestUtils.invokeMethod(rateService, "applyCurrencyFilter", currencyFilter);

        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains("USD"));
        assertTrue(result.contains("EUR"));
        assertTrue(result.contains("JPY"));
        verify(geckoApiClient, times(1)).getSupportedCurrencies();
    }
}