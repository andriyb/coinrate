package com.codingchallenge.coinrate.rategateway.web.dto;

import com.codingchallenge.coinrate.rategateway.service.dto.HistoryRateDto;

import java.util.List;

public class CoinRatesFormDTO {

    private List<String> availableCoins;

    private String selectedCoin;

    private String ipAddress;

    private LocaleDto locale;

    private CurrentRateDto currentRate;

    private  List<HistoryRateDto> historyRates;

    private String token;

    public CoinRatesFormDTO() {
    }

    public CoinRatesFormDTO(List<String> availableCoins, String selectedCoin, String ipAddress, List<HistoryRateDto> historyRates,
                            CurrentRateDto currentRate, LocaleDto locale) {

        this.availableCoins = availableCoins;
        this.selectedCoin = selectedCoin;
        this.ipAddress = ipAddress;
        this.historyRates = historyRates;
        this.currentRate = currentRate;

        this.locale = locale;
    }

    public List<String> getAvailableCoins() {
        return availableCoins;
    }

    public void setAvailableCoins(List<String> availableCoins) {
        this.availableCoins = availableCoins;
    }

    public List<HistoryRateDto> getHistoryRates() {
        return historyRates;
    }

    public void setHistoryRates(List<HistoryRateDto> historyRates) {
        this.historyRates = historyRates;
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


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public CurrentRateDto getCurrentRate() {
        return currentRate;
    }

    public void setCurrentRate(CurrentRateDto currentRate) {
        this.currentRate = currentRate;
    }

    public LocaleDto getLocale() {
        return locale;
    }

    public void setLocale(LocaleDto locale) {
        this.locale = locale;
    }
}
