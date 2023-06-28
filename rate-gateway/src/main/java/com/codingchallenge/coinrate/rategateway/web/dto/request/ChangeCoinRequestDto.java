package com.codingchallenge.coinrate.rategateway.web.dto.request;

/**
 * Data transfer object for requesting a change in the current coin rate.
 * Extends the UpdateCurrentRateRequestDto class.
 */
public class ChangeCoinRequestDto extends UpdateCurrentRateRequestDto {

    private Integer daysCount;

    /**
     * Returns the number of exchange history days count.
     *
     * @return The number of days count.
     */
    public Integer getDaysCount() {
        return daysCount;
    }

    /**
     * Sets the number of exchange history days count.
     *
     * @param daysCount The number of days count.
     */
    public void setDaysCount(Integer daysCount) {
        this.daysCount = daysCount;
    }
}
