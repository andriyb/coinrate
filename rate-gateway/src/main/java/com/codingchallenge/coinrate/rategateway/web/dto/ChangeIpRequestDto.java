package com.codingchallenge.coinrate.rategateway.web.dto;

public class ChangeIpRequestDto extends ChangeCoinRequestDto{

    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
