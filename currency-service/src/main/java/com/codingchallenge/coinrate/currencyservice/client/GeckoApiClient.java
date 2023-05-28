package com.codingchallenge.coinrate.currencyservice.client;

import com.codingchallenge.coinrate.currencyservice.client.data.CoinList;
import com.codingchallenge.coinrate.currencyservice.client.data.HistoryCoin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The Coingecko API feign client.
 */
@FeignClient(name = "coinGeckoApiClient", url = "https://api.coingecko.com/api/v3")
public interface GeckoApiClient {

    /**
     * The format data used in Coingecko API.
     */
    String GECKO_API_DATE_FORMAT = "d-MM-uuuu";

    /**
     * Gets supported currencies from Coingecko API.
     *
     * @return The supported currencies.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/simple/supported_vs_currencies")
    ResponseEntity<List<String>> getSupportedCurrencies();

    /**
     * Gets list of supported coins from Coingecko API.
     *
     * @return The coin list.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/coins/list")
    ResponseEntity<List<CoinList>> getCoinList();

    /**
     * Gets coin history with exchange rates for all supported currencies in Coingecko API.
     *
     * @param coinCode The coin code.
     * @param date     The date to filter data.
     * @return The coin data by date.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/coins/{coinCode}/history?date={date}&localization=false")
    ResponseEntity<HistoryCoin> getCoinByDate(@PathVariable("coinCode") String coinCode, @PathVariable("date") String date);

}
