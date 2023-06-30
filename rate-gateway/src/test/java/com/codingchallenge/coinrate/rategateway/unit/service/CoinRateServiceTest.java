package com.codingchallenge.coinrate.rategateway.unit.service;

import com.codingchallenge.coinrate.rategateway.client.CurrencyServiceClient;
import com.codingchallenge.coinrate.rategateway.client.dto.*;
import com.codingchallenge.coinrate.rategateway.service.CoinRateService;
import com.codingchallenge.coinrate.rategateway.service.dto.form.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CoinRateService Tests")
public class CoinRateServiceTest {

    private final Locale germanLocale = new Locale("de", "DE");

    @Mock
    private CurrencyServiceClient currencyServiceClient;

    @InjectMocks
    private CoinRateService coinRateService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        coinRateService = new CoinRateService(currencyServiceClient);
    }

    @Test
    @DisplayName("Test getCurrentRate - Successful Response")
    public void testGetCurrentRate_SuccessfulResponse() {

        CurrentRateDto currentRateDto = new CurrentRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                new BigDecimal("123.45"), LocalDateTime.parse("2023-06-25T19:34:50.63"));

        ResponseEntity<CurrentRateDto> responseEntity = new ResponseEntity<>(currentRateDto, HttpStatus.OK);
        Mockito.when(currencyServiceClient.getCurrentRate(currentRateDto.getCoinCode(),
                currentRateDto.getCurrencyCode().toLowerCase())).thenReturn(responseEntity);

        CurrentRateFormDto result = coinRateService.getCurrentRate(currentRateDto.getCoinCode(),
                currentRateDto.getCurrencyCode(), germanLocale);

        assertNotNull(result);
        assertEquals("10 btc = 1.234,50\u00a0€", result.getFormattedRate());
    }

    @Test
    @DisplayName("Test getCurrentRate - Empty Response")
    public void testGetCurrentRate_EmptyResponse() {

        String coinCode = "btc";
        String currencyCode = "eur";

        ResponseEntity<CurrentRateDto> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(currencyServiceClient.getCurrentRate(coinCode, currencyCode.toLowerCase()))
                .thenReturn(responseEntity);

        CurrentRateFormDto result = coinRateService.getCurrentRate(coinCode, currencyCode, germanLocale);

        assertNotNull(result);
        assertNull(result.getFormattedRate());
    }

    @Test
    @DisplayName("Test getRateHistory - Successful Response")
    public void testGetRateHistory_SuccessfulResponse() {

        String coinCode = "btc";
        String currencyCode = "eur";
        Integer daysCount = 7;

        List<HistoryRateDto> historyRateList = Arrays.asList(
                new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                        new BigDecimal("123.45"), LocalDate.parse("2023-06-25")));

        ResponseEntity<List<HistoryRateDto>> responseEntity = new ResponseEntity<>(historyRateList, HttpStatus.OK);
        Mockito.when(currencyServiceClient.getRateHistory(coinCode, currencyCode, daysCount))
                .thenReturn(responseEntity);

        List<HistoryRateFormDto> result =
                coinRateService.getRateHistory(coinCode, currencyCode, daysCount, germanLocale);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("10 btc = 1.234,50\u00a0€", result.get(0).getDisplayRate());
    }

    @Test
    @DisplayName("Test getRateHistory - Empty Response")
    public void testGetRateHistory_EmptyResponse() {
        String coinCode = "btc";
        String currencyCode = "eur";
        Integer daysCount = 7;

        ResponseEntity<List<HistoryRateDto>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(currencyServiceClient.getRateHistory(coinCode, currencyCode, daysCount))
                .thenReturn(responseEntity);

        List<HistoryRateFormDto> result =
                coinRateService.getRateHistory(coinCode, currencyCode, daysCount, germanLocale);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test getFormRate - Successful Response")
    public void testGetFormRate_SuccessfulResponse() {
        String coinCode = "btc";
        String currencyCode = "eur";
        Integer daysCount = 7;
        List<String> supportedCoins = Arrays.asList("bitcoin", "ethereum", "action-coin");
        String ipAddress = "127.0.0.1";

        LocaleFormDto localeForm = new LocaleFormDto("Germany","DE", "German", "de", "Euro", "EUR", true, true);


        HistorySettingsFormDto historySettingsForm =
                new HistorySettingsFormDto(7, 30);

        List<HistoryRateDto> historyRateList = Arrays.asList(
                new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                        new BigDecimal("123.45"), LocalDate.parse("2023-06-24")));

        FormRateDto formRateDto = new FormRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                new BigDecimal("123.45"), LocalDateTime.parse("2023-06-25T19:34:50.63"), historyRateList);

        ResponseEntity<FormRateDto> responseEntity = new ResponseEntity<>(formRateDto, HttpStatus.OK);
        Mockito.when(currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount)).thenReturn(responseEntity);

        CoinRateFormDto result = coinRateService.getFormRate(coinCode, currencyCode, daysCount, supportedCoins,
                ipAddress, localeForm, historySettingsForm, germanLocale);

        assertNotNull(result);
        assertEquals("10 btc = 1.234,50\u00a0€", result.getCurrentRateForm().getFormattedRate());
    }

    @Test
    @DisplayName("Test getFormRate - Empty Response")
    public void testGetFormRate_EmptyResponse() {

        Integer daysCount = 7;
        List<String> supportedCoins = Arrays.asList("bitcoin", "ethereum", "action-coin");
        String ipAddress = "192.168.1.1";
        String coinCode = supportedCoins.get(0);
        String currencyCode = "EUR";


        LocaleFormDto localeForm = new LocaleFormDto("Germany","DE", "German", "de", "Euro", "EUR", true, true);

        HistorySettingsFormDto historySettingsForm = new HistorySettingsFormDto(7, 30);


        ResponseEntity<FormRateDto> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount)).thenReturn(responseEntity);

        CoinRateFormDto result = coinRateService.getFormRate(coinCode, currencyCode, daysCount, supportedCoins,
                ipAddress, localeForm, historySettingsForm, germanLocale);

        assertNotNull(result);
        assertNull(result.getCurrentRateForm());
    }

    @Test
    @DisplayName("Test getSupportedCoins - Successful Response")
    public void testGetSupportedCoins_SuccessfulResponse() {

        List<CoinList> coinList = Arrays.asList(
                new CoinList("bitcoin", "btc", "Bitcoin"),
                new CoinList("ethereum", "eth", "Ethereum"),
                new CoinList("action-coin", "actn", "Action Coin")
        );

        ResponseEntity<List<CoinList>> responseEntity = new ResponseEntity<>(coinList, HttpStatus.OK);
        Mockito.when(currencyServiceClient.getSupportedCoins()).thenReturn(responseEntity);

        List<String> result = coinRateService.getSupportedCoins();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertTrue(result.contains("bitcoin"));
        assertTrue(result.contains("ethereum"));
        assertTrue(result.contains("action-coin"));
    }

    @Test
    @DisplayName("Test getSupportedCoins - Empty Response")
    public void testGetSupportedCoins_EmptyResponse() {
        ResponseEntity<List<CoinList>> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(currencyServiceClient.getSupportedCoins()).thenReturn(responseEntity);

        List<String> result = coinRateService.getSupportedCoins();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Test getHistorySettings - Successful Response")
    public void testGetHistorySettings_SuccessfulResponse() {
        HistorySettingsDto historySettingsDto = new HistorySettingsDto(30, 7);

        ResponseEntity<HistorySettingsDto> responseEntity = new ResponseEntity<>(historySettingsDto, HttpStatus.OK);
        Mockito.when(currencyServiceClient.getHistorySettings()).thenReturn(responseEntity);

        HistorySettingsFormDto result = coinRateService.getHistorySettings();

        assertNotNull(result);
        assertEquals(historySettingsDto.getHistoryDaysCountDefault(), result.getHistoryDaysCountDefault());
        assertEquals(historySettingsDto.getHistoryDaysCountLimit(), result.getHistoryDaysCountLimit());
    }

    @Test
    @DisplayName("Test getHistorySettings - Empty Response")
    public void testGetHistorySettings_EmptyResponse() {
        ResponseEntity<HistorySettingsDto> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(currencyServiceClient.getHistorySettings()).thenReturn(responseEntity);

        HistorySettingsFormDto result = coinRateService.getHistorySettings();

        assertNotNull(result);
        assertEquals(1, result.getHistoryDaysCountDefault());
        assertEquals(1, result.getHistoryDaysCountLimit());
    }
}