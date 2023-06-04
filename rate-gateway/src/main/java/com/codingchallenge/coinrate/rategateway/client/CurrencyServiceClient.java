package com.codingchallenge.coinrate.rategateway.client;


//import com.codingchallenge.coinrate.rategateway.client.data.FormRateDto;
//import com.codingchallenge.coinrate.rategateway.client.data.HistoryRateDto;
//import com.codingchallenge.coinrate.rategateway.client.data.RateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.CurrencyLocaleDto;

import com.codingchallenge.coinrate.rategateway.client.data.CoinList;
import com.codingchallenge.coinrate.rategateway.service.dto.FormRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.RateDto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Currency Service API feign client.
 */
@FeignClient(name = "${microservices.currencyService.serviceName}")
public interface CurrencyServiceClient {

    /**
     * Retrieves a list of currency locales from LocaleService based on the provided IP address.
     *
     * @param ip The IP address.
     * @return The currency locale list.
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.localeService.urlPath}${microservices.localeService.currencyLocalesEndpoint}")
    ResponseEntity<List<CurrencyLocaleDto>> getCurrencyLocales(@RequestParam("ip") String ip);

    /**
     * Load rates history into microservice database from the Coingecko API
     *
     * @return HTTP response with status code.
     */
    @PutMapping("/api/v1/load-history")
    public ResponseEntity<?> loadHistory();

    /**
     * Delete rates history from microservice database.
     *
     * @return HTTP response with status code.
     */
    @DeleteMapping("/api/v1/delete-rate-history")
    public ResponseEntity<?> deleteRateHistory();

    /**
     * Retrieves the current rate for a specific coin and currency from the Currency Service.
     *
     * @param coinCode     the code of the coin
     * @param currencyCode the code of the currency
     * @return the ResponseEntity containing the current rate
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.currencyService.urlPath}${microservices.currencyService.currentRateEndpoint}")
    ResponseEntity<RateDto> getCurrentRate(@RequestParam("coinCode") String coinCode,
                                           @RequestParam("currencyCode") String currencyCode);

    /**
     * Retrieves the rate history for a specific coin and currency from the Currency Service.
     *
     * @param coinCode     the code of the coin
     * @param currencyCode the code of the currency
     * @param daysCount    the number of days for which to retrieve the rate history (optional)
     * @return the ResponseEntity containing the rate history
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.currencyService.urlPath}${microservices.currencyService.rateHistoryEndpoint}")
    ResponseEntity<List<HistoryRateDto>> getRateHistory(@RequestParam(name="coin-code") String coinCode,
                                                        @RequestParam(name="currency-code") String currencyCode,
                                                        @RequestParam(name="days-count",required=false) Integer daysCount);

    /**
     * Retrieves the form rate for a specific coin and currency from the Currency Service.
     *
     * @param coinCode     the code of the coin
     * @param currencyCode the code of the currency
     * @param daysCount    the number of days for which to retrieve the form rate (optional)
     * @return the ResponseEntity containing the form rate
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.currencyService.urlPath}${microservices.currencyService.formRateEndpoint}")
    ResponseEntity<FormRateDto> getFormRate(@RequestParam(name="coin-code") String coinCode,
                                            @RequestParam(name="currency-code") String currencyCode,
                                            @RequestParam(name="days-count",required=false) Integer daysCount);

    /**
     * Retrieves the list of supported coins from the Currency Service.
     *
     * @return the ResponseEntity containing the list of supported coins
     */
    @RequestMapping(method = RequestMethod.GET,
            value = "${microservices.currencyService.urlPath}${microservices.currencyService.supportedCoins}")
    ResponseEntity<List<CoinList>> getSupportedCoins();

}
