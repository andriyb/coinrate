package com.codingchallenge.coinrate.rategateway.service.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * Utility class for rate scaling operations.
 */
public class RateScaleUtil {

    /**
     * Calculates the scale shift required to achieve the desired precision and scale for a rate.
     *
     * @param rate              The rate to calculate the scale shift for.
     * @param desiredPrecision  The desired precision.
     * @param desiredScale      The desired scale.
     * @return The scale shift value.
     */
    public static Integer calcScaleShift(BigDecimal rate, Integer desiredPrecision, Integer desiredScale) {

        int scaleDifference = desiredScale - rate.scale();

        // Calculate the maximum precision by considering the scale difference
        int maxPrecision = Math.min(rate.precision() + scaleDifference, desiredPrecision);

        // Calculate the difference between the desired precision and the maximum precision
        int precisionDifference = desiredPrecision - maxPrecision;

        // Determine the final shift value by taking the maximum of the scale difference and the precision difference
        int shift = Math.max(scaleDifference, precisionDifference);
        return Math.max(shift, 0);
    }

    /**
     * Formats and scales a rate using the desired precision, scale, coin symbol, and currency format.
     *
     * @param rate              The rate to format and scale.
     * @param desiredPrecision  The desired precision.
     * @param desiredScale      The desired scale.
     * @param coinSymbol        The symbol of the coin.
     * @param currencyFormat    The format for currency display.
     * @return The formatted and scaled rate.
     */
    public static String getScaledRate(BigDecimal rate, Integer desiredPrecision, Integer desiredScale,
                                       String coinSymbol, NumberFormat currencyFormat) {

        int calcScaleShift = calcScaleShift(rate, desiredPrecision, desiredScale);

        // Scale the rate by multiplying it with 10 raised to the power of the scale shift
        BigDecimal result = rate.multiply(BigDecimal.TEN.pow(calcScaleShift));

        // Return a formatted string representing the scaled rate
        return ((int) Math.pow(10, calcScaleShift)) + " " + coinSymbol + " = "
                + currencyFormat.format(result.setScale(desiredScale, RoundingMode.HALF_UP));

    }
}
