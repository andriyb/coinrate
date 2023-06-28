package com.codingchallenge.coinrate.rategateway.web.dto.request;

/**
 * Data transfer object for requesting a change of IP address.
 * Extends the ChangeCoinRequestDto class.
 */
public class ChangeIpRequestDto extends ChangeCoinRequestDto {

    private String ipAddress;

    /**
     * Returns the IP address.
     *
     * @return The IP address.
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets the IP address.
     *
     * @param ipAddress The IP address.
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
