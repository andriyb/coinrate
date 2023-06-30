package com.codingchallenge.coinrate.rategateway.service.dto.form;

/**
 * Represents the formatted information for a historical rate.
 */
public class HistoryRateFormDto {

    private String formattedRateDate;
    private String formattedRate;

    /**
     * Constructor for HistoryRateFormDto with formatted rate and rate date.
     *
     * @param formattedRate     The formatted rate to be set.
     * @param formattedRateDate The formatted rate date to be set.
     */
    public HistoryRateFormDto(String formattedRate, String formattedRateDate) {
        this.formattedRate = formattedRate;
        this.formattedRateDate = formattedRateDate;
    }

    /**
     * Gets the formatted rate date.
     *
     * @return The formatted rate date.
     */
    public String getFormattedRateDate() {
        return formattedRateDate;
    }

    /**
     * Sets the formatted rate date.
     *
     * @param formattedRateDate The formatted rate date to be set.
     */
     public void setFormattedRateDate(String formattedRateDate) {
        this.formattedRateDate = formattedRateDate;
    }

    /**
     * Gets the formatted rate.
     *
     * @return The formatted rate.
     */
    public String getFormattedRate() {
        return formattedRate;
    }

    /**
     * Sets the formatted rate.
     *
     * @param formattedRate The formatted rate to be set.
     */
    public void setFormattedRate(String formattedRate) {
        this.formattedRate = formattedRate;
    }
}
