package com.codingchallenge.coinrate.currencyservice.client.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The type to request market data from the Coingecko API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketData {
    @JsonProperty("current_price")
    private Map<String, BigDecimal> currentPrice;

    /**
     * Gets current price.
     *
     * @return the current price
     */
    public Map<String, BigDecimal> getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Sets current price.
     *
     * @param currentPrice the current price
     */
    public void setCurrentPrice(Map<String, BigDecimal> currentPrice) {
        this.currentPrice = currentPrice;
    }
}
