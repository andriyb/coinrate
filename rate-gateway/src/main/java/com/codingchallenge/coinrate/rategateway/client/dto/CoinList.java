package com.codingchallenge.coinrate.rategateway.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CoinList {

    @JsonProperty("id")
    private String code;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("name")
    private String name;

    public CoinList() {
    }

    public CoinList(String code, String symbol, String name) {
        this.code = code;
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * Gets the coin code.
     *
     * @return The coin code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the coin code.
     *
     * @param code The coin code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the coin symbol.
     *
     * @return The coin symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Sets the coin symbol.
     *
     * @param symbol The coin symbol.
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the coin name.
     *
     * @return The coin name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the coin name.
     *
     * @param name The coin name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
