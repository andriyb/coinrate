package com.codingchallenge.coinrate.currencyservice.client.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * The type to request market chart data from the Coingecko API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarketChart {
    @JsonProperty("prices")
    private List<List<String>> prices;
    @JsonProperty("market_caps")
    private List<List<String>> marketCaps;
    @JsonProperty("total_volumes")
    private List<List<String>> totalVolumes;

    /**
     * Gets prices.
     *
     * @return the prices
     */
    public List<List<String>> getPrices() {
        return prices;
    }

    /**
     * Sets prices.
     *
     * @param prices the prices
     */
    public void setPrices(List<List<String>> prices) {
        this.prices = prices;
    }

    /**
     * Gets market caps.
     *
     * @return the market caps
     */
    public List<List<String>> getMarketCaps() {
        return marketCaps;
    }

    /**
     * Sets market caps.
     *
     * @param marketCaps the market caps
     */
    public void setMarketCaps(List<List<String>> marketCaps) {
        this.marketCaps = marketCaps;
    }

    /**
     * Gets total volumes.
     *
     * @return the total volumes
     */
    public List<List<String>> getTotalVolumes() {
        return totalVolumes;
    }

    /**
     * Sets total volumes.
     *
     * @param totalVolumes the total volumes
     */
    public void setTotalVolumes(List<List<String>> totalVolumes) {
        this.totalVolumes = totalVolumes;
    }

}
