package com.codingchallenge.coinrate.rategateway.service.dto.form;

import java.util.List;

/**
 * Data transfer object for cryptocurrency rates form.
 */
public class CoinRateFormDto {

    private List<String> supportedCoins;
    private String selectedCoin;

    private String ipAddress;

    private LocaleFormDto localeForm;

    private CurrentRateFormDto currentRateForm;

    private List<HistoryRateFormDto> historyRateForms;

    private HistorySettingsFormDto historySettings;

    private String token;

    /**
     * Default constructor.
     */
    public CoinRateFormDto() {
    }

    /**
     * Parameterized constructor to initialize CoinRateFormDto with the provided values.
     *
     * @param supportedCoins   The list of supported coin codes.
     * @param selectedCoin     The selected coin code.
     * @param ipAddress        The IP address.
     * @param localeForm           The locale information.
     * @param currentRateForm      The current rate.
     * @param historyRateList     The history rates.
     * @param historySettings  The history settings.
     */
    public CoinRateFormDto(List<String> supportedCoins, String selectedCoin, String ipAddress, LocaleFormDto localeForm,
                           CurrentRateFormDto currentRateForm, List<HistoryRateFormDto> historyRateList,
                           HistorySettingsFormDto historySettings) {

        this.supportedCoins = supportedCoins;
        this.selectedCoin = selectedCoin;
        this.ipAddress = ipAddress;
        this.localeForm = localeForm;
        this.currentRateForm = currentRateForm;
        this.historyRateForms = historyRateList;
        this.historySettings = historySettings;
    }

    /**
     * Returns the list of supported coin codes.
     *
     * @return The supported coin codes.
     */
    public List<String> getSupportedCoins() {
        return supportedCoins;
    }

    /**
     * Sets the list of supported coin codes.
     *
     * @param supportedCoins The supported coin codes.
     */
    public void setSupportedCoins(List<String> supportedCoins) {
        this.supportedCoins = supportedCoins;
    }

    /**
     * Returns the selected coin code.
     *
     * @return The selected coin code.
     */
    public String getSelectedCoin() {
        return selectedCoin;
    }

    /**
     * Sets the selected coin code.
     *
     * @param selectedCoin The selected coin code.
     */
    public void setSelectedCoin(String selectedCoin) {
        this.selectedCoin = selectedCoin;
    }

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

    /**
     * Returns the locale information.
     *
     * @return The locale information.
     */
    public LocaleFormDto getLocaleForm() {
        return localeForm;
    }

    /**
     * Sets the locale information.
     *
     * @param localeForm The locale information.
     */
    public void setLocaleForm(LocaleFormDto localeForm) {
        this.localeForm = localeForm;
    }

    /**
     * Returns the current rate.
     *
     * @return The current rate.
     */
    public CurrentRateFormDto getCurrentRateForm() {
        return currentRateForm;
    }

    /**
     * Sets the current rate.
     *
     * @param currentRateForm The current rate.
     */
    public void setCurrentRateForm(CurrentRateFormDto currentRateForm) {
        this.currentRateForm = currentRateForm;
    }

    /**
     * Returns the history rates.
     *
     * @return The history rates.
     */
    public List<HistoryRateFormDto> getHistoryRateForms() {
        return historyRateForms;
    }

    /**
     * Sets the history rates.
     *
     * @param historyRateForms The history rates.
     */
    public void setHistoryRateForms(List<HistoryRateFormDto> historyRateForms) {
        this.historyRateForms = historyRateForms;
    }

    /**
     * Returns the history settings.
     *
     * @return The history settings.
     */
    public HistorySettingsFormDto getHistorySettings() {
        return historySettings;
    }

    /**
     * Sets the history settings.
     *
     * @param historySettings The history settings.
     */
    public void setHistorySettings(HistorySettingsFormDto historySettings) {
        this.historySettings = historySettings;
    }

    /**
     * Returns the token.
     *
     * @return The token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token The token.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
