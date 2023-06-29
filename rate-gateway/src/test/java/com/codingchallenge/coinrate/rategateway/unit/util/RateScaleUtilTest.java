package com.codingchallenge.coinrate.rategateway.unit.util;

import com.codingchallenge.coinrate.rategateway.service.util.RateScaleUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

class RateScaleUtilTest {

    @Test
    @DisplayName("Checking scaling shift calculation in RateScaleUtil")
    void testCalcScaleShift() {
        // Test case 1: rate.scale() < desiredScale
        BigDecimal rate1 = new BigDecimal("0.00123");
        Integer desiredPrecision1 = 5;
        Integer desiredScale1 = 3;
        Assertions.assertEquals(4, RateScaleUtil.calcScaleShift(rate1, desiredPrecision1, desiredScale1));

        // Test case 2: rate.scale() > desiredScale
        BigDecimal rate2 = new BigDecimal("123.45");
        Integer desiredPrecision2 = 6;
        Integer desiredScale2 = 2;
        Assertions.assertEquals(1, RateScaleUtil.calcScaleShift(rate2, desiredPrecision2, desiredScale2));

        // Test case 3: rate.scale() == desiredScale
        BigDecimal rate3 = new BigDecimal("1.234");
        Integer desiredPrecision3 = 4;
        Integer desiredScale3 = 3;
        Assertions.assertEquals(0, RateScaleUtil.calcScaleShift(rate3, desiredPrecision3, desiredScale3));
    }

    @Test
    @DisplayName("Checking scaling shift calculation in RateScaleUtil")
    void testGetScaledRate() {
        BigDecimal rate = new BigDecimal("123.456");
        Integer desiredPrecision = 6;
        Integer desiredScale = 2;
        String coinSymbol = "btc";
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

        String expectedFormattedRate = "10 btc = $1,234.56";
        Assertions.assertEquals(expectedFormattedRate, RateScaleUtil.getScaledRate(rate, desiredPrecision, desiredScale, coinSymbol, currencyFormat));
    }
}