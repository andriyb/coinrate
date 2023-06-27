package com.codingchallenge.coinrate.currencyservice.service;

import com.codingchallenge.coinrate.currencyservice.client.GeckoApiClient;
import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import com.codingchallenge.coinrate.currencyservice.domain.RateHistory;
import com.codingchallenge.coinrate.currencyservice.repository.RateHistoryRepository;
import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import com.codingchallenge.coinrate.currencyservice.service.dto.CurrentRateDto;
import com.codingchallenge.coinrate.currencyservice.service.dto.FormRateDto;
import com.codingchallenge.coinrate.currencyservice.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.currencyservice.service.dto.HistorySettingsDto;
import com.codingchallenge.coinrate.currencyservice.service.mapper.CurrentRateMapper;
import com.codingchallenge.coinrate.currencyservice.service.mapper.HistoryRateMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.codingchallenge.coinrate.currencyservice.client.GeckoApiClient.GECKO_API_DATE_FORMAT;

/**
 * Service class for handling currency rates using Coingecko API. Since the number of Coingecko API
 * free calls is limited, it is necessary to store the history data in a local database. This database
 * has a specific length for the coin exchange history and is populated when the microservice is launched
 * without previously prepared history data.
 *
 * @see <a href="https://www.coingecko.com/en/api/documentation">Coingecko API Documentation</a>
 */
@Service
public class RateService implements ApplicationRunner {

    private final RateHistoryRepository rateHistoryRepository;
    private final GeckoApiClient geckoApiClient;

    @Value("${currency-service.history-days-count-limit}")
    private Integer historyDaysCountLimit;

    @Value("${currency-service.history-days-count-default}")
    private Integer historyDaysCountDefault;

    @Value("${currency-service.query-per-min-limit}")
    private Integer queryPerMinLimit;

    @Value("${currency-service.currency-filter}")
    private List<String> currencyFilter;

    @Value("${currency-service.coin-filter}")
    private List<String> coinFilter;

    private static final Logger logger = LogManager.getLogger(RateService.class);

    @Autowired
    public RateService(RateHistoryRepository rateHistoryRepository, GeckoApiClient geckoApiClient) {
        this.rateHistoryRepository = rateHistoryRepository;
        this.geckoApiClient = geckoApiClient;
    }

    /**
     * Loading the coins history data while the microservice startup.
     *
     * @param args The application arguments.
     */
    @Override
    public void run(ApplicationArguments args) {
        loadRatesHistory();
    }

    /**
     * Applies the currency filter to obtain a set of required currency codes.
     *
     * @param currencyFilter The set of currency codes to filter.
     * @return A set of required currency codes after applying the filter.
     */
    private Set<String> applyCurrencyFilter(Set<String> currencyFilter) {

        // Reading all available currency code from the external service and applying the filter to get
        // Set of the required currency codes.
        ResponseEntity<List<String>> supportedCurrencyResponse = geckoApiClient.getSupportedCurrencies();
        HttpStatus supportedCurrencyStatus = supportedCurrencyResponse.getStatusCode();

        if (supportedCurrencyStatus == HttpStatus.OK) {
            List<String> currenciesAvailable = supportedCurrencyResponse.getBody();

            if (currenciesAvailable != null) {
                return currenciesAvailable.stream()
                        .filter(currencyFilter::contains)
                        .collect(Collectors.toSet());
            }
        }

        // Return an empty set if the currency codes are not available or the response status is not OK.
        return Collections.emptySet();
    }

    /**
     * Applies the coin filter to get the set of required coin objects.
     *
     * @param coinFilter The set of coin codes to filter the available coins.
     * @return The set of coin objects that match the filter criteria.
     */
    private Set<CoinList> applyCoinFilter(Set<String> coinFilter) {

        // Reading all available coins as CoinList from the external service and applying the filter to get
        // Set of the required coin objects.
        ResponseEntity<List<CoinList>> supportedCoinsResponse = geckoApiClient.getCoinList();
        HttpStatus supportedCoinsStatus = supportedCoinsResponse.getStatusCode();

        if (supportedCoinsStatus == HttpStatus.OK) {
            List<CoinList> coinsAvailable = supportedCoinsResponse.getBody();

            if (coinsAvailable != null) {
                return coinsAvailable.stream()
                        .filter(coinList -> coinFilter.contains(coinList.getCode()))
                        .collect(Collectors.toSet());
            }
        }

        // Return an empty set if the coin codes are not available or the response status is not OK.
        return Collections.emptySet();
    }

    /**
     * Checks if all the required currencies and coins are matched in the existing history.
     *
     * @param existRates         The list of existing rate history records.
     * @param currenciesRequired The set of required currency codes.
     * @param coinsRequired      The set of required coin objects.
     * @return true if there are unmatched currencies or coins in the history rates, false otherwise.
     */
    private boolean unmatchedHistoryCheck(List<RateHistory> existRates, Set<String> currenciesRequired,
                                          Set<CoinList> coinsRequired) {

        // Check if all the required currencies are matched in the existing rates history
        boolean allCurrenciesMatched = !existRates.isEmpty() && !currenciesRequired.isEmpty() &&
                currenciesRequired.stream()
                        .allMatch(currencyCode -> existRates.stream()
                                .anyMatch(rateHistory ->
                                        rateHistory.getCurrencyCode().equals(currencyCode)));

        // Check if all the required coins are matched in the existing rates history
        boolean allCoinsMatched = !existRates.isEmpty() && !coinsRequired.isEmpty() &&
                existRates.stream()
                        .map(RateHistory::getCoinCode)
                        .allMatch(coinCode -> coinsRequired.stream()
                                .map(CoinList::getCode)
                                .anyMatch(coinCode::equals));

        // Return true if all the required currencies and coins are matched, false otherwise
        return !allCurrenciesMatched || !allCoinsMatched;
    }


    /**
     * Loads the rates history with default filters by currencies and coins with default history length.
     */
    public void loadRatesHistory() {

        loadRatesHistory(new HashSet<>(currencyFilter), new HashSet<>(coinFilter), historyDaysCountLimit);
    }


    /**
     * Loads the rates history for the specified currency and coin filters.
     *
     * @param currencyFilter The set of currency filters.
     * @param coinFilter     The set of coin filters.
     * @param daysCount      The number of days to load rates history for.
     */
    public void loadRatesHistory(Set<String> currencyFilter, Set<String> coinFilter, int daysCount) {
        logger.info("Start loading rates history...");

        // Apply currency filter to get the set of required currency codes
        Set<String> currenciesRequired = applyCurrencyFilter(currencyFilter);

        // Apply coin filter to get the set of required coin objects
        Set<CoinList> coinsRequired = applyCoinFilter(coinFilter);

        // Get the date of yesterday as the last history date
        LocalDate yesterday = LocalDate.now().minusDays(1);

        // Create a DateTimeFormatter using the GECKO_API_DATE_FORMAT pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GECKO_API_DATE_FORMAT);

        // Calculate the delay in milliseconds for querying the Coingecko API based on the query per minute limit
        long delay = 60000 / queryPerMinLimit;

        // Iterate over a stream of dates starting from yesterday and going back in time
        Stream.iterate(yesterday, date -> date.minusDays(1))
                .limit(daysCount)
                .forEach(date -> {
                    // Format the current date using the specified formatter
                    String currentDate = date.format(formatter);

                    // Print the current date being processed
                    logger.info("Processing the date: " + currentDate);

                    // Retrieve existing rates from the repository for the current date
                    List<RateHistory> existRates = rateHistoryRepository.findAllByDate(date);

                    // Check if there are unmatched history records for the required currencies and coins
                    if (unmatchedHistoryCheck(existRates, currenciesRequired, coinsRequired)) {
                        // Delete existing rates if there are any
                        if (!existRates.isEmpty()) {
                            rateHistoryRepository.deleteAllByDate(date);
                        }

                        // Retrieve the historical coin data for each required coin
                        for (CoinList currentCoin : coinsRequired) {
                            // Make an API call to get the coin history for each date
                            ResponseEntity<HistoryCoin> coinHistoryResponse =
                                    geckoApiClient.getCoinByDate(currentCoin.getCode(), currentDate);

                            try {
                                // Introduce a delay to comply with the query per minute limit
                                Thread.sleep(delay);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            HttpStatus coinHistoryResponseStatus = coinHistoryResponse.getStatusCode();

                            // If the API call was successful, save the rate history to the repository
                            if (coinHistoryResponseStatus == HttpStatus.OK) {
                                List<RateHistory> rateHistoryList =
                                        HistoryRateMapper.toEntityList(coinHistoryResponse.getBody(), date);
                                rateHistoryRepository.saveAll(rateHistoryList);
                            }
                        }
                    }
                });

        logger.info("Rates are loaded into the database.");
    }

    /**
     * Deletes all rate history.
     */
    public void deleteAll() {

        rateHistoryRepository.deleteAll();
    }

    /**
     * Retrieves the current rate for the specified coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @return The current rate data.
     */
    @Cacheable(value = "rateCache", key = "#coinCode + '-' + #currencyCode")
    public CurrentRateDto getCurrentRate(String coinCode, String currencyCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(GECKO_API_DATE_FORMAT);
        ResponseEntity<HistoryCoin> coinHistoryResponse =
                geckoApiClient.getCoinByDate(coinCode, LocalDate.now().format(formatter));
        HttpStatus coinHistoryResponseStatus = coinHistoryResponse.getStatusCode();

        if (coinHistoryResponseStatus == HttpStatus.OK) {
            return CurrentRateMapper.toDto(coinHistoryResponse.getBody(), currencyCode, LocalDateTime.now());
        }

        return null;
    }

    /**
     * Retrieves the rate history for the specified coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days to retrieve rate history for.
     * @return A list of rate history data.
     */
    public List<HistoryRateDto> getRateHistory(String coinCode, String currencyCode, Integer daysCount) {
        List<RateHistory> rateHistory = rateHistoryRepository.findAllByDateRange(coinCode, currencyCode,
                LocalDate.now().minusDays(historyDaysCalc(daysCount)), LocalDate.now().minusDays(1));
        return HistoryRateMapper.toDtoList(rateHistory);
    }

    /**
     * Retrieves the current rate and rate history for the specified coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days to retrieve rate history for.
     * @return The current rate and rate history data.
     */
    public FormRateDto getFormRate(String coinCode, String currencyCode, Integer daysCount) {
        CurrentRateDto currentRateDto = getCurrentRate(coinCode, currencyCode);
        List<HistoryRateDto> history = getRateHistory(coinCode, currencyCode, historyDaysCalc(daysCount));

        return new FormRateDto(
                coinCode, currencyCode, currentRateDto.getCoinName(), currentRateDto.getCoinSymbol(),
                currentRateDto.getCurrentRate(), currentRateDto.getRateDateTime(), history);
    }

    /**
     * Calculates the number of history days to retrieve based on the specified days count.
     *
     * @param daysCount The number of days specified.
     * @return The number of history days to retrieve.
     */
    private int historyDaysCalc(Integer daysCount) {
        if (daysCount == null || daysCount > historyDaysCountLimit) {
            return historyDaysCountLimit;
        } else {
            return daysCount;
        }
    }
    @Cacheable(value = "supportedCoinsCache")
    public List<CoinList> getSupportedCoins() {

        ResponseEntity<List<CoinList>> coinListResponse = geckoApiClient.getCoinList();
        HttpStatus coinListResponseStatus = coinListResponse.getStatusCode();

        if (coinListResponseStatus == HttpStatus.OK) {
            return coinListResponse.getBody().stream()
                    .filter(coinList -> coinFilter.contains(coinList.getCode()))
                    .collect(Collectors.toList());
        }

        return null;
    }

    /**
     * Returns History Settings.
     *
     * @return The HistorySettingsDto object.
     */
    public HistorySettingsDto getHistorySettings() {

        return new HistorySettingsDto(historyDaysCountLimit, historyDaysCountDefault);
    }

}
