package com.codingchallenge.coinrate.currencyservice.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codingchallenge.coinrate.currencyservice.client.GeckoApiClient;
import com.codingchallenge.coinrate.currencyservice.domain.RateHistory;
import com.codingchallenge.coinrate.currencyservice.repository.RateHistoryRepository;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@PropertySource("classpath:application-test.properties")
@EnableConfigurationProperties
@ContextConfiguration(classes = {RateService.class})
@ExtendWith(SpringExtension.class)
class RateServiceTest {
    @MockBean
    private GeckoApiClient geckoApiClient;

    @MockBean
    private RateHistoryRepository rateHistoryRepository;

    @Autowired
    private RateService rateService;

    /**
     * Method under test: {@link RateService#loadRatesHistory(Set, Set, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLoadRatesHistory() {
        // TODO: Complete this test.
        //   Reason: R033 Missing Spring properties.
        //   Failed to create Spring context due to unresolvable @Value
        //   properties: private java.lang.Integer com.codingchallenge.coinrate.currencyservice.service.RateService.historyLengthLimit
        //   Please check that at least one of the property files is provided
        //   and contains required variables:
        //   - application-test.properties (file missing)
        //   See https://diff.blue/R033 to resolve this issue.

        // Arrange
        // TODO: Populate arranged inputs
        Set<String> currencyFilter = null;
        Set<String> coinFilter = null;
        int daysCount = 0;

        // Act
        this.rateService.loadRatesHistory(currencyFilter, coinFilter, daysCount);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link RateService#loadRatesHistory(Set, Set, int)}
     */
    @Test
    void testLoadRatesHistory2() {
        when(rateHistoryRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(new ArrayList<>());
        when(geckoApiClient.getCoinList()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        HashSet<String> currencyFilter = new HashSet<>();
        rateService.loadRatesHistory(currencyFilter, new HashSet<>(), 3);
        verify(rateHistoryRepository, atLeast(1)).findAllByDate(Mockito.<LocalDate>any());
        verify(geckoApiClient).getCoinList();
        verify(geckoApiClient).getSupportedCurrencies();
    }

    /**
     * Method under test: {@link RateService#loadRatesHistory(Set, Set, int)}
     */
    @Test
    void testLoadRatesHistory3() {
        RateHistory rateHistory = new RateHistory();
        rateHistory.setCoinCode("Start loading rates history...");
        rateHistory.setCoinName("Start loading rates history...");
        rateHistory.setCoinSymbol("Start loading rates history...");
        rateHistory.setCurrencyCode("GBP");
        rateHistory.setDate(LocalDate.of(1970, 1, 1));
        rateHistory.setId(1L);
        rateHistory.setRate(BigDecimal.valueOf(1L));

        ArrayList<RateHistory> rateHistoryList = new ArrayList<>();
        rateHistoryList.add(rateHistory);
        doNothing().when(rateHistoryRepository).deleteAllByDate(Mockito.<LocalDate>any());
        when(rateHistoryRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(rateHistoryList);
        when(geckoApiClient.getCoinList()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        HashSet<String> currencyFilter = new HashSet<>();
        rateService.loadRatesHistory(currencyFilter, new HashSet<>(), 3);
        verify(rateHistoryRepository, atLeast(1)).findAllByDate(Mockito.<LocalDate>any());
        verify(rateHistoryRepository, atLeast(1)).deleteAllByDate(Mockito.<LocalDate>any());
        verify(geckoApiClient).getCoinList();
        verify(geckoApiClient).getSupportedCurrencies();
    }

    /**
     * Method under test: {@link RateService#loadRatesHistory(Set, Set, int)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLoadRatesHistory4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.codingchallenge.coinrate.currencyservice.service.RateService.applyCoinFilter(RateService.java:117)
        //       at com.codingchallenge.coinrate.currencyservice.service.RateService.loadRatesHistory(RateService.java:187)
        //   See https://diff.blue/R013 to resolve this issue.

        RateHistory rateHistory = new RateHistory();
        rateHistory.setCoinCode("Start loading rates history...");
        rateHistory.setCoinName("Start loading rates history...");
        rateHistory.setCoinSymbol("Start loading rates history...");
        rateHistory.setCurrencyCode("GBP");
        rateHistory.setDate(LocalDate.of(1970, 1, 1));
        rateHistory.setId(1L);
        rateHistory.setRate(BigDecimal.valueOf(1L));

        ArrayList<RateHistory> rateHistoryList = new ArrayList<>();
        rateHistoryList.add(rateHistory);
        doNothing().when(rateHistoryRepository).deleteAllByDate(Mockito.<LocalDate>any());
        when(rateHistoryRepository.findAllByDate(Mockito.<LocalDate>any())).thenReturn(rateHistoryList);
        when(geckoApiClient.getCoinList()).thenReturn(null);
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        HashSet<String> currencyFilter = new HashSet<>();
        rateService.loadRatesHistory(currencyFilter, new HashSet<>(), 3);
    }
}

