package com.codingchallenge.coinrate.rategateway.web.mapper;
import com.codingchallenge.coinrate.rategateway.service.dto.CurrentRateDto;
import com.codingchallenge.coinrate.rategateway.web.dto.CurrentRateFormDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrentRateFormDtoMapper {


    public static Integer calcScaleShift(BigDecimal aNumber, Integer desiredPrecision, Integer desiredScale) {

        int numberScale = aNumber.scale();
        int numberPrecision = aNumber.precision();

        int scaleDifference = desiredScale - numberScale;
        int maxPrecision = Math.min(numberPrecision + scaleDifference, desiredPrecision);
        int precisionDifference = desiredPrecision - maxPrecision;

        int shift = Math.max(scaleDifference, precisionDifference);
        if (shift < 0) {
            return  0;
        } else {
            return shift;
        }
    }

    public static CurrentRateFormDto mapClientToFormDto(CurrentRateDto currentRateDto, Locale locale) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

        int desiredPrecision = 6;
        int desiredScale = 2;
        BigDecimal rate = currentRateDto.getCurrentRate();

        Integer calcScaleShift = calcScaleShift(rate, desiredPrecision, desiredScale);
        BigDecimal result = rate.multiply(BigDecimal.TEN.pow(calcScaleShift));
        String displayRate = ((int) Math.pow(10, calcScaleShift)) + " " + currentRateDto.getCoinSymbol() + " = "
                +currencyFormat.format(result.setScale(desiredScale, RoundingMode.HALF_UP));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy HH:mm:ss").withLocale(locale);

        if (currentRateDto.getRateDateTime() != null) {
            String displayUpdateDateTime = dateFormatter.format(currentRateDto.getRateDateTime());
            return new CurrentRateFormDto(displayRate, displayUpdateDateTime);
        } else {
            return new CurrentRateFormDto(displayRate, "");
        }
    }
}
