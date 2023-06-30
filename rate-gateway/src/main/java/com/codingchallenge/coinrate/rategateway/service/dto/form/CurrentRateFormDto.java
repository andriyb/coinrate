package com.codingchallenge.coinrate.rategateway.service.dto.form;

/**
 * Represents the current rate display information.
 */
public class CurrentRateFormDto {

    private String formattedRate;
    private String formattedUpdateDateTime;
    private String formattedUpdateRequestDateTime;

    /**
     * Default constructor for CurrentRateFormDto.
     */
    public CurrentRateFormDto() {
    }

    /**
     * Constructor for CurrentRateFormDto with display rate and update date/time.
     *
     * @param formattedRate          The formatted rate to be set.
     * @param formattedUpdateDateTime The formatted update date/time to be set.
     * @param formattedUpdateRequestDateTime The formatted update request date/time to be set.
     */
    public CurrentRateFormDto(String formattedRate, String formattedUpdateDateTime,
                              String formattedUpdateRequestDateTime) {
        this.formattedRate = formattedRate;
        this.formattedUpdateDateTime = formattedUpdateDateTime;
        this.formattedUpdateRequestDateTime = formattedUpdateRequestDateTime;
    }

    /**
     * Gets the formatted rate.
     *
     * @return The display rate.
     */
    public String getFormattedRate() {
        return formattedRate;
    }

    /**
     * Sets the formatted rate.
     *
     * @param formattedRate The display rate to be set.
     */
    public void setFormattedRate(String formattedRate) {
        this.formattedRate = formattedRate;
    }

    /**
     * Gets the formatted update date/time.
     *
     * @return The formatted update date/time.
     */
    public String getFormattedUpdateDateTime() {
        return formattedUpdateDateTime;
    }

    /**
     * Sets the formatted update date/time.
     *
     * @param formattedUpdateDateTime The formatted update date/time to be set.
     */
    public void setFormattedUpdateDateTime(String formattedUpdateDateTime) {
        this.formattedUpdateDateTime = formattedUpdateDateTime;
    }

    /**
     * Gets the formatted update request date/time.
     *
     * @return The formatted update request date/time.
     */
    public String getFormattedUpdateRequestDateTime() {
        return formattedUpdateRequestDateTime;
    }

    /**
     * Sets the formatted update request date/time.
     *
     * @param formattedUpdateRequestDateTime The formatted update request date/time to be set.
     */
    public void setFormattedUpdateRequestDateTime(String formattedUpdateRequestDateTime) {
        this.formattedUpdateRequestDateTime = formattedUpdateRequestDateTime;
    }
}
