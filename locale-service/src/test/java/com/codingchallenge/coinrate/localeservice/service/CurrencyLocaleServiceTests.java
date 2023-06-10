package com.codingchallenge.coinrate.localeservice.service;

import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;
import com.codingchallenge.coinrate.localeservice.service.CurrencyLocaleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@DisplayName("CurrencyLocaleService tests")
public class CurrencyLocaleServiceTests {

    public static final String KNOWN_NL_IP_ADDRESS = "185.253.96.178";
    public static final String NL_COUNTRY_CODE = "NL";
    public static final String DE_COUNTRY_CODE = "DE";
    public static final String NL_LANG_CODE = "nl";
    public static final String DE_LANG_CODE = "de";
    public static final String EUR_CURRENCY_CODE = "EUR";

    public static final String UNKNOWN_IP_ADDRESS = "0.0.0.0";

    @Autowired
    private CurrencyLocaleService currencyLocaleService;

    @DisplayName("Should return the correct currency locale for a known IP address in the Netherlands")
    @Test
    void getNetherlands() {

        CurrencyLocaleDto toCompare =
                new CurrencyLocaleDto(NL_COUNTRY_CODE, NL_LANG_CODE, EUR_CURRENCY_CODE, true);

        // Get the CurrencyLocaleDto object from the service method for a known IP address in Netherlands
        CurrencyLocaleDto res = currencyLocaleService.getCurrencyLocales(KNOWN_NL_IP_ADDRESS).get(0);

        // Verify that the returned CurrencyLocaleDto object has the expected values for Netherlands
        Assertions.assertTrue(toCompare.getCountryCode().equals(res.getCountryCode()) &&
                toCompare.getLangCode().equals(res.getLangCode()) &&
                toCompare.getCurrencyCode().equals(res.getCurrencyCode()) &&
                toCompare.isDefaultCurrency() == res.isDefaultCurrency());

    }

    @DisplayName("Should return default currency locale for an unknown IP address")
    @Test
    void hostNotFound() {

        CurrencyLocaleDto toCompare =
                new CurrencyLocaleDto(DE_COUNTRY_CODE, DE_LANG_CODE, EUR_CURRENCY_CODE, true);

        // Get the CurrencyLocaleDto object from the service method for an unknown IP address
        CurrencyLocaleDto res = currencyLocaleService.getCurrencyLocales(UNKNOWN_IP_ADDRESS).get(0);

        // Verify that the returned CurrencyLocaleDto object has the expected values for the default locale
        Assertions.assertTrue(toCompare.getCountryCode().equals(res.getCountryCode()) &&
                        toCompare.getLangCode().equals(res.getLangCode()) &&
                        toCompare.getCurrencyCode().equals(res.getCurrencyCode()) &&
                        toCompare.isDefaultCurrency() == res.isDefaultCurrency());
    }

    @DisplayName("Should return the correct ISO country code for a known IP address")
    @Test
    void getIsoCountryCodeNetherlands() throws Exception {

        String toCompare = NL_COUNTRY_CODE;

        // Get the ISO country code from the service method for a known IP address in Netherlands
        String res = currencyLocaleService.getISOCountryCode(KNOWN_NL_IP_ADDRESS);

        // Verify that the returned ISO country code matches the expected value for Netherlands
        Assertions.assertTrue(toCompare.equals(res));

    }

    @DisplayName("Should return an empty string for the ISO country code of an unknown IP address")
    @Test
    void getIsoCountryCodeHostNotFound() throws Exception {

        String toCompare = "";

        // Get the ISO country code from the service method for an unknown IP address
        String res = currencyLocaleService.getISOCountryCode(UNKNOWN_IP_ADDRESS);

        // Verify that the returned ISO country code matches the expected empty string
        Assertions.assertTrue(toCompare.equals(res));

    }

}