package com.codingchallenge.coinrate.currencyservice.service;

import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import com.codingchallenge.coinrate.currencyservice.domain.RateHistory;
import com.codingchallenge.coinrate.currencyservice.repository.RateHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
@DisplayName("RateService currency and coin filters matching tests")
@ActiveProfiles("test")
public class RateServiceCurrenciesAndCoinsMatchingTest {

    @Mock
    private RateHistoryRepository rateHistoryRepository;

    @InjectMocks
    private RateService rateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    @DisplayName("Should return false when all currencies and coins have matched history")
    void unmatchedHistoryCheckWhenAllCurrenciesAndCoinsMatched() {
        List<RateHistory> existRates = new ArrayList<>();
        existRates.add(new RateHistory(1L, "usd", "bitcoin", "btc",
                "Bitcoin", BigDecimal.valueOf(50000), LocalDate.now()));

        existRates.add(new RateHistory(2L, "usd", "ethereum", "eth",
                "Ethereum",BigDecimal.valueOf(2000), LocalDate.now()));

        Set<String> currenciesRequired = new HashSet<>(List.of("usd"));
        Set<CoinList> coinsRequired = new HashSet<>(Arrays.asList(
                new CoinList("bitcoin", "btc", "Bitcoin"),
                new CoinList("ethereum", "eth", "Ethereum")));

        boolean result = Boolean.TRUE.equals(ReflectionTestUtils.invokeMethod(rateService,
                "unmatchedHistoryCheck", existRates, currenciesRequired, coinsRequired));

        assertFalse(result);
    }

    @Test
    @DisplayName("Should return true when all coins have matched history but currencies have unmatched history")
    void unmatchedHistoryCheckWhenAllCoinsMatchedButCurrenciesUnmatched() {
        List<RateHistory> existRates = new ArrayList<>();
        existRates.add(new RateHistory(1L, "usd", "bitcoin", "btc",
                "Bitcoin", BigDecimal.valueOf(50000), LocalDate.now()));

        existRates.add(new RateHistory(2L, "usd", "ethereum", "eth",
                "Ethereum",BigDecimal.valueOf(2000), LocalDate.now()));

        Set<String> currenciesRequired = new HashSet<>();
        currenciesRequired.add("usd");
        currenciesRequired.add("eur");

        Set<CoinList> coinsRequired = new HashSet<>();
        coinsRequired.add(new CoinList("bitcoin", "btc", "Bitcoin"));
        coinsRequired.add(new CoinList("ethereum", "eth", "Ethereum"));

        boolean result = Boolean.TRUE.equals(ReflectionTestUtils.invokeMethod(rateService,
                "unmatchedHistoryCheck", existRates, currenciesRequired, coinsRequired));

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return true when unmatched history is found for currencies and coins")
    void unmatchedHistoryCheckWhenUnmatchedHistoryFound() {
        List<RateHistory> existRates = new ArrayList<>();
        existRates.add(new RateHistory(1L, "usd", "bitcoin", "btc",
                "Bitcoin", BigDecimal.valueOf(50000), LocalDate.now()));

        existRates.add(new RateHistory(2L, "usd", "ethereum", "eth",
                "Ethereum",BigDecimal.valueOf(2000), LocalDate.now()));

        Set<String> currenciesRequired = new HashSet<>();
        currenciesRequired.add("bitcoin");
        currenciesRequired.add("ethereum");
        currenciesRequired.add("litecoin");

        Set<CoinList> coinsRequired = new HashSet<>();
        coinsRequired.add(new CoinList("bitcoin", "btc", "Bitcoin"));
        coinsRequired.add(new CoinList("ethereum", "eth", "Ethereum"));
        coinsRequired.add(new CoinList("litecoin", "ltc","Litecoin"));

        boolean result = Boolean.TRUE.equals(ReflectionTestUtils.invokeMethod(rateService,
                "unmatchedHistoryCheck", existRates, currenciesRequired, coinsRequired));

        assertTrue(result);
    }

    @Test
    @DisplayName("Should return true when all currencies have matched history but coins have unmatched history")
    void unmatchedHistoryCheckWhenAllCurrenciesMatchedButCoinsUnmatched() {
        List<RateHistory> existRates = new ArrayList<>();
        existRates.add(new RateHistory(1L, "usd", "bitcoin", "btc",
                "Bitcoin", BigDecimal.valueOf(50000), LocalDate.now()));

        existRates.add(new RateHistory(2L, "usd", "ethereum", "eth",
                "Ethereum",BigDecimal.valueOf(2000), LocalDate.now()));

        Set<String> currenciesRequired = new HashSet<>(List.of("USD"));
        Set<CoinList> coinsRequired = new HashSet<>();
        coinsRequired.add(new CoinList("bitcoin", "btc", "Bitcoin"));
        coinsRequired.add(new CoinList("ethereum", "eth", "Ethereum"));
        coinsRequired.add(new CoinList("litecoin", "ltc","Litecoin"));

        boolean result = Boolean.TRUE.equals(ReflectionTestUtils.invokeMethod(rateService,
                "unmatchedHistoryCheck", existRates, currenciesRequired, coinsRequired));

        assertTrue(result);
    }

}