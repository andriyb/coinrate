package com.codingchallenge.coinrate.rategateway.web.dto;

import com.codingchallenge.coinrate.rategateway.service.dto.HistorySettingsDto;

import java.util.List;

public class RateFormDto {

    private List<String> supportedCoins;
    private String selectedCoin;

    private String ipAddress;

    private LocaleFormDto locale;

    private CurrentRateFormDto currentRate;

    private List<HistoryRateFormDto> historyRates;

    private HistorySettingsDto historySettings;

    private String token;

    public RateFormDto() {
    }

    public RateFormDto(List<String> supportedCoins, String selectedCoin,
                       String ipAddress,
                       LocaleFormDto locale,
                       CurrentRateFormDto currentRate,
                       List<HistoryRateFormDto> historyRates,
                       HistorySettingsDto historySettings) {

        this.supportedCoins = supportedCoins;
        this.selectedCoin = selectedCoin;

        this.ipAddress = ipAddress;

        this.locale = locale;

        this.currentRate = currentRate;

        this.historyRates = historyRates;

        this.historySettings = historySettings;
    }

    public List<String> getSupportedCoins() {
        return supportedCoins;
    }

    public void setSupportedCoins(List<String> supportedCoins) {
        this.supportedCoins = supportedCoins;
    }

    public String getSelectedCoin() {
        return selectedCoin;
    }

    public void setSelectedCoin(String selectedCoin) {
        this.selectedCoin = selectedCoin;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocaleFormDto getLocale() {
        return locale;
    }

    public void setLocale(LocaleFormDto locale) {
        this.locale = locale;
    }

    public CurrentRateFormDto getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(CurrentRateFormDto currentRate) {
        this.currentRate = currentRate;
    }

    public List<HistoryRateFormDto> getHistoryRates() {
        return historyRates;
    }

    public void setHistoryRates(List<HistoryRateFormDto> historyRates) {
        this.historyRates = historyRates;
    }

    public HistorySettingsDto getHistorySettings() {
        return historySettings;
    }

    public void setHistorySettings(HistorySettingsDto historySettings) {
        this.historySettings = historySettings;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
