package com.codingchallenge.coinrate.localeservice.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Represents the currency locale entity.
 */
@Entity
@Table(name = "currency_locale")
public class CurrencyLocale implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "lang_code")
    private String langCode;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "default_currency")
    private boolean defaultCurrency;

    /**
     * Default constructor.
     */
    public CurrencyLocale() {}

    /**
     * Constructs a CurrencyLocale object with the provided information.
     *
     * @param countryCode    The 2-letter country code defined in ISO 3166.
     * @param langCode       The 2-letter language code defined in ISO 639.
     * @param currencyCode   The 3 or 4-letter currency code.
     * @param defaultCurrency Specifies if it is the default currency for the locale.
     */
    public CurrencyLocale(String countryCode, String langCode, String currencyCode, boolean defaultCurrency) {
        this.countryCode = countryCode;
        this.langCode = langCode;
        this.currencyCode = currencyCode;
        this.defaultCurrency = defaultCurrency;
    }

    /**
     * Gets the 2-letter country code in ISO 3166.
     *
     * @return The country code.
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the 2-letter country code in ISO 3166.
     *
     * @param countryCode The country code to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * Gets the 2-letter language code defined in ISO 639.
     *
     * @return The language code.
     */
    public String getLangCode() {
        return langCode;
    }

    /**
     * Sets the 2-letter language code defined in ISO 639.
     *
     * @param langCode The language code to set.
     */
    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    /**
     * Gets the 3 or 4-letter currency code.
     *
     * @return The currency code.
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the 3 or 4-letter currency code.
     *
     * @param currencyCode The currency code to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Checks if the currency locale is the default currency for the locale.
     *
     * @return true if it is the default currency, false otherwise.
     */
    public boolean isDefaultCurrency() {
        return defaultCurrency;
    }

    /**
     * Sets whether the currency locale is the default currency for the locale.
     *
     * @param defaultCurrency Specifies if it is the default currency.
     */
    public void setDefaultCurrency(boolean defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
