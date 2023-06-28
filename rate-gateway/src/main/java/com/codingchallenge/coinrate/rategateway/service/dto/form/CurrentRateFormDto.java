package com.codingchallenge.coinrate.rategateway.service.dto.form;

/**
 * Represents the current rate display information.
 */
public class CurrentRateFormDto {

    private String displayRate;
    private String displayUpdateDateTime;

    /**
     * Default constructor for CurrentRateFormDto.
     */
    public CurrentRateFormDto() {
    }

    /**
     * Constructor for CurrentRateFormDto with display rate and update date/time.
     *
     * @param displayRate          The display rate to be set.
     * @param displayUpdateDateTime The display update date/time to be set.
     */
    public CurrentRateFormDto(String displayRate, String displayUpdateDateTime) {
        this.displayRate = displayRate;
        this.displayUpdateDateTime = displayUpdateDateTime;
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

    /**
     * Gets the display update date/time.
     *
     * @return The display update date/time.
     */
    public String getDisplayUpdateDateTime() {
        return displayUpdateDateTime;
    }

    /**
     * Sets the display update date/time.
     *
     * @param displayUpdateDateTime The display update date/time to be set.
     */
    public void setDisplayUpdateDateTime(String displayUpdateDateTime) {
        this.displayUpdateDateTime = displayUpdateDateTime;
    }
}
