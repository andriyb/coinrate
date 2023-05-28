package com.codingchallenge.coinrate.currencyservice.client.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type to request coin history data from the Coingecko API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoryCoin  extends  CoinList{

    @JsonProperty("market_data")
    private MarketData marketData;

    /**
     * Gets the market data.
     *
     * @return the market data
     */
    public MarketData getMarketData() {
        return marketData;
    }

    /**
     * Sets the market data.
     *
     * @param marketData the market data
     */
    public void setMarketData(MarketData marketData) {
        this.marketData = marketData;
    }
}
