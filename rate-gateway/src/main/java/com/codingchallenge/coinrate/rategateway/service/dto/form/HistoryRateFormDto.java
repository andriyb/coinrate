package com.codingchallenge.coinrate.rategateway.service.dto.form;

/**
 * Represents the display information for a historical rate.
 */
public class HistoryRateFormDto {

    private String displayRateDate;
    private String displayRate;

    /**
     * Constructor for HistoryRateFormDto with display rate and rate date.
     *
     * @param displayRate     The display rate to be set.
     * @param displayRateDate The display rate date to be set.
     */
    public HistoryRateFormDto(String displayRate, String displayRateDate) {
        this.displayRate = displayRate;
        this.displayRateDate = displayRateDate;
    }

    /**
     * Gets the display rate date.
     *
     * @return The display rate date.
     */
    public String getDisplayRateDate() {
        return displayRateDate;
    }

    /**
     * Sets the display rate date.
     *
     * @param displayRateDate The display rate date to be set.
     */
     public void setDisplayRateDate(String displayRateDate) {
        this.displayRateDate = displayRateDate;
    }

    /**
     * Gets the display rate.
     *
     * @return The display rate.
     */
    public String getDisplayRate() {
        return displayRate;
    }

    /**
     * Sets the display rate.
     *
     * @param displayRate The display rate to be set.
     */
    public void setDisplayRate(String displayRate) {
        this.displayRate = displayRate;
    }
}
