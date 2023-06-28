package com.codingchallenge.coinrate.rategateway.service.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.LocaleFormDto;

import java.util.Currency;
import java.util.Locale;

/**
 * Mapper class for converting currency locale settings between the microservice and form data transfer objects.
 */
public class LocaleFormDtoMapper {

    /**
     * Maps a CurrencyLocaleDto object to a LocaleFormDto object.
     *
     * @param currencyLocaleDto The CurrencyLocaleDto object to be mapped.
     * @param currentLocale     The current Locale object.
     * @return The mapped LocaleFormDto object.
     */
    public static LocaleFormDto mapClientToFormDto(CurrencyLocaleDto currencyLocaleDto, Locale currentLocale) {

        String currencyCode = currencyLocaleDto.getCurrencyCode().toUpperCase();

        // Get the display name of the country based on the ISO 3166 country code
        return new LocaleFormDto(currentLocale.getDisplayCountry(), currencyLocaleDto.getCountryCode(),
                // Get the display name of the language based on the ISO 639 language code
                currentLocale.getDisplayLanguage(), currencyLocaleDto.getLangCode(),
                // Get the display name of the currency based on the ISO 4217 currency code
                Currency.getInstance(currencyCode).getDisplayName(), currencyLocaleDto.getCurrencyCode(),
                currencyLocaleDto.isDefaultCurrency(),  currencyLocaleDto.isLocaleNotFound());
    }
}
