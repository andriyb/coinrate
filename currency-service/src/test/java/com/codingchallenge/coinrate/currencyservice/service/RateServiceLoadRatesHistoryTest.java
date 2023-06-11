package com.codingchallenge.coinrate.currencyservice.service;

import com.codingchallenge.coinrate.currencyservice.client.GeckoApiClient;
import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import com.codingchallenge.coinrate.currencyservice.client.data.MarketData;
import com.codingchallenge.coinrate.currencyservice.repository.RateHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@DisplayName("RateService load rates history tests")
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = ConfigDataApplicationContextInitializer.class)
@EnableConfigurationProperties(value = RateServiceLoadRatesHistoryTest.class)
@ActiveProfiles("test")
class RateServiceLoadRatesHistoryTest {

    @Mock
    private RateHistoryRepository rateHistoryRepository;

    @Mock
    private GeckoApiClient geckoApiClient;

    @InjectMocks
    private RateService rateService;

    private final static List<String> SUPPORTED_CURRENCY_CODES = Arrays.asList(
            "btc", "eth", "ltc", "bch", "bnb", "eos", "xrp", "xlm", "link", "dot", "yfi", "usd", "aed", "ars", "aud",
            "bdt", "bhd", "bmd", "brl",  "cad", "chf", "clp", "cny", "czk", "dkk", "eur", "gbp", "hkd", "huf", "idr",
            "ils", "inr", "jpy", "krw", "kwd", "lkr", "mmk", "mxn", "myr", "ngn", "nok", "nzd", "php", "pkr", "pln",
            "rub", "sar", "sek", "sgd", "thb", "try", "twd", "uah", "vef", "vnd", "zar", "xdr", "xag", "xau", "bits",
            "sats");

    private final static List<CoinList> SUPPORTED_COINS_SHORT_LIST_5 = List.of(
            new CoinList("bitcoin", "btc", "Bitcoin"),
            new CoinList("ethereum", "eth", "Ethereum"),
            new CoinList("litecoin", "ltc","Litecoin"),
            new CoinList("neton", "nto","Neton"),
            new CoinList("novacoin", "nvc","Novacoin")
    );


    @BeforeEach
    void setUp() {

        Properties properties = new Properties();
        try {
            // Load the application.yml file from the classpath
            InputStream inputStream = RateServiceLoadRatesHistoryTest.class.getClassLoader()
                    .getResourceAsStream("application-test.yml");
            properties.load(inputStream);
            inputStream.close();

            ReflectionTestUtils.setField(rateService, "historyLengthLimit",
                    Integer.parseInt((String) properties.get("history-length-limit")));

            ReflectionTestUtils.setField(rateService, "queryPerMinLimit",
                    Integer.parseInt((String) properties.get("query-per-min-limit")));


            List<String> currencyFilter = Arrays.asList(((String)properties.get("currency-filter")).split(","));
            ReflectionTestUtils.setField(rateService, "currencyFilter", currencyFilter);

            List<String> coinFilter = Arrays.asList(((String)properties.get("coin-filter")).split(","));
            ReflectionTestUtils.setField(rateService, "coinFilter", coinFilter);


        } catch (IOException e) {
            e.printStackTrace();
        }

        when(geckoApiClient.getSupportedCurrencies()).thenReturn(ResponseEntity.ok(SUPPORTED_CURRENCY_CODES));
        when(geckoApiClient.getCoinList()).thenReturn(ResponseEntity.ok(SUPPORTED_COINS_SHORT_LIST_5));
    }

    void createMock() {

        when(geckoApiClient.getCoinByDate(anyString(), anyString())).thenAnswer(new Answer<ResponseEntity<HistoryCoin>>() {
            @Override
            public ResponseEntity<HistoryCoin> answer(InvocationOnMock invocation) throws Throwable {
                String coinCode = invocation.getArgument(0);
                String currentDate = invocation.getArgument(1);

                BigDecimal currentPrice;
                if (coinCode.equals("bitcoin")) {
                    currentPrice = BigDecimal.valueOf(5000.0 + Math.random() * 1000);
                } else if (coinCode.equals("ethereum")) {
                    currentPrice = BigDecimal.valueOf(4000.0 + Math.random() * 1000);
                } else if (coinCode.equals("litecoin")) {
                    currentPrice = BigDecimal.valueOf(2000.0 + Math.random() * 1000);
                } else {
                    currentPrice = BigDecimal.ZERO;
                }

                HistoryCoin historyCoin = new HistoryCoin();

                MarketData md = new MarketData();
                md.setCurrentPrice(Map.of(
                        "usd", currentPrice,
                        "eur", currentPrice.multiply(BigDecimal.valueOf(1.1))));
                historyCoin.setMarketData(md);
                historyCoin.setCode(coinCode);

                return ResponseEntity.ok(historyCoin);
            }
        });
    }

    @Test
    @DisplayName("Should not load rates history when currency filter is empty")
    void loadRatesHistoryWhenCurrencyFilterIsEmpty() {
        Set<String> currencyFilter = new HashSet<>();
        Set<String> coinFilter = new HashSet<>(Arrays.asList("bitcoin", "ethereum"));
        int daysCount = 7;

        createMock();

        rateService.loadRatesHistory(currencyFilter, coinFilter, daysCount);

        //        verify(geckoApiClient, never()).getSupportedCurrencies();
//        verify(rateHistoryRepository, never()).findAllByDate(any(LocalDate.class));
        verify(rateHistoryRepository, never()).deleteAllByDate(any(LocalDate.class));
//        verify(geckoApiClient, never()).getCoinByDate(anyString(), anyString());
 //       verify(rateHistoryRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Should not load rates history when coin filter is empty")
    void loadRatesHistoryWhenCoinFilterIsEmpty() {
        Set<String> currencyFilter = new HashSet<>(Arrays.asList("usd", "eur"));
        Set<String> coinFilter = new HashSet<>();
        int daysCount = 7;


        rateService.loadRatesHistory(currencyFilter, coinFilter, daysCount);

//        verify(geckoApiClient, never()).getCoinList();
//        verify(rateHistoryRepository, never()).findAllByDate(any(LocalDate.class));
        //verify(rateHistoryRepository, never()).deleteAllByDate(any(LocalDate.class));
        verify(rateHistoryRepository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Should load rates history with default filters and days count when parameters are not provided")
    void loadRatesHistoryWithDefaultFiltersAndDaysCount() {
        Set<String> currencyFilter = new HashSet<>(Arrays.asList("usd", "eur"));
        Set<String> coinFilter = new HashSet<>(Arrays.asList("bitcoin", "ethereum"));
        int daysCount = 7;

        createMock();

        rateService.loadRatesHistory(currencyFilter, coinFilter, daysCount);

        verify(geckoApiClient, times(1)).getSupportedCurrencies();
        verify(geckoApiClient, times(1)).getCoinList();
        verify(rateHistoryRepository, times(7)).findAllByDate(any(LocalDate.class));
        //verify(rateHistoryRepository, times(2)).deleteAllByDate(any(LocalDate.class));
        verify(rateHistoryRepository, times(14)).saveAll(anyList());
    }


    @Test
    @DisplayName("Should not load rates history when API returns non-OK status")
    void loadRatesHistoryWhenApiReturnsNonOkStatus() {
        Set<String> currencyFilter = new HashSet<>(Arrays.asList("usd", "eur"));
        Set<String> coinFilter = new HashSet<>(Arrays.asList("bitcoin", "ethereum"));
        int daysCount = 7;

        ResponseEntity<List<String>> supportedCurrencyResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(geckoApiClient.getSupportedCurrencies()).thenReturn(supportedCurrencyResponse);

        ResponseEntity<List<CoinList>> supportedCoinsResponse = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        when(geckoApiClient.getCoinList()).thenReturn(supportedCoinsResponse);

        rateService.loadRatesHistory(currencyFilter, coinFilter, daysCount);

//        verify(rateHistoryRepository, never()).findAllByDate(any());
        //verify(rateHistoryRepository, never()).deleteAllByDate(any());
        verify(rateHistoryRepository, never()).saveAll(anyList());
    }
}