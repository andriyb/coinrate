package com.codingchallenge.coinrate.rategateway.service;

import com.codingchallenge.coinrate.rategateway.client.CurrencyServiceClient;
import com.codingchallenge.coinrate.rategateway.client.LocaleServiceClient;
import com.codingchallenge.coinrate.rategateway.client.data.CoinList;
import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.FormRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.RateDto;
import org.apache.http.conn.util.InetAddressUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for handling currency locales using LocaleService.
 */
@Service
public class RateService {
    final LocaleServiceClient localeServiceClient;
    final CurrencyServiceClient currencyServiceClient;

    @Value("${rate-gateway.default-country-code}")
    private String defaultCountryCode;

    @Value("${rate-gateway.default-lang-code}")
    private String defaultLangCode;

    @Value("${rate-gateway.default-currency-code}")
    private String defaultCurrencyCode;

    @Autowired
    public RateService(LocaleServiceClient localeServiceClient, CurrencyServiceClient currencyServiceClient) {

        this.localeServiceClient = localeServiceClient;
        this.currencyServiceClient = currencyServiceClient;
    }

    /**
     * Checks if the provided IP address is valid.
     *
     * @param ipAddress The IP address to check.
     * @return true if the IP address is valid, false otherwise.
     */
    public boolean isValidIPAddress(String ipAddress) {

        if(ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }

        if(!InetAddressUtils.isIPv4Address(ipAddress) && !InetAddressUtils.isIPv6Address(ipAddress)) {
            return false;
        }

        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress instanceof java.net.Inet4Address || inetAddress instanceof java.net.Inet6Address;
        } catch (UnknownHostException e) {
            return false;
        }

    }

    /**
     * Retrieves a list of currency locales based on the provided IP address.
     *
     * @param ipAddress The IP address.
     * @return The currency locale list.
     */
    public List<CurrencyLocaleDto> getLocales(String ipAddress) {

        List<CurrencyLocaleDto> currencyLocales = new ArrayList<>();

        if(!isValidIPAddress(ipAddress)) {
            currencyLocales.add(
                    new CurrencyLocaleDto(defaultCountryCode, defaultLangCode, defaultCurrencyCode, true));
            return currencyLocales;
        }

        // Retrieve the list of currency locales from the locale service client
        ResponseEntity<List<CurrencyLocaleDto>> responseEntity =
                localeServiceClient.getCurrencyLocales(ipAddress);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            currencyLocales = responseEntity.getBody();
        }

        // Return the list of currency locales
        return currencyLocales;
    }

    /**
     * Retrieves the current rate for a specific coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @return The current rate.
     */
    @Cacheable(value = "rateCache", key = "#coinCode + '-' + #currencyCode")
    public RateDto getCurrentRate(String coinCode, String currencyCode) {

        RateDto rateDto = new RateDto();

        // Call the currency service client to get the current rate
        ResponseEntity<RateDto> responseEntity = currencyServiceClient.getCurrentRate(coinCode, currencyCode);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            rateDto = responseEntity.getBody();
        }

        return rateDto;
    }

    /**
     * Retrieves the rate history for a specific coin and currency.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days for the rate history.
     * @return The rate history.
     */
    public List<HistoryRateDto> getRateHistory(String coinCode, String currencyCode, Integer daysCount) {

        List<HistoryRateDto> historyRates = new ArrayList<>();

        // Call the currency service client to get the rate history
        ResponseEntity<List<HistoryRateDto>> responseEntity = currencyServiceClient.getRateHistory(coinCode, currencyCode, daysCount);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            historyRates = responseEntity.getBody();
        }

        return historyRates;
    }

    /**
     * Retrieves the rate and history for a specific coin and currency, suitable for a form.
     *
     * @param coinCode     The code of the coin.
     * @param currencyCode The code of the currency.
     * @param daysCount    The number of days for the rate history.
     * @return The form rate.
     */
    public FormRateDto getFormRate(String coinCode, String currencyCode, Integer daysCount) {

        FormRateDto formRateDto = new FormRateDto();

        // Call the currency service client to get the form rate
        ResponseEntity<FormRateDto> responseEntity = currencyServiceClient.getFormRate(coinCode, currencyCode, daysCount);

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            formRateDto = responseEntity.getBody();
        }

        return formRateDto;
    }

    /**
     * Retrieves a list of supported coins.
     *
     * @return The list of supported coins.
     */
    public List<String> getSupportedCoins() {

        List<String> supportedCoinsList = new ArrayList<>();

        // Call the currency service client to get the supported coins
        ResponseEntity<List<CoinList>> responseEntity = currencyServiceClient.getSupportedCoins();

        if (responseEntity != null &&
                HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            supportedCoinsList = responseEntity.getBody().stream()
                    .map(coinCode -> coinCode.getCode())
                    .collect(Collectors.toList());
        }

        return supportedCoinsList;
    }

}
