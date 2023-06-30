package com.codingchallenge.coinrate.rategateway.service.mapper;
import com.codingchallenge.coinrate.rategateway.client.dto.CurrentRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.CurrentRateFormDto;
import com.codingchallenge.coinrate.rategateway.service.util.RateScaleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Mapper class for converting cryptocurrency current rate data between the microservice and form data transfer objects.
 */
public class CurrentRateFormDtoMapper {

    private static final Logger logger = LogManager.getLogger(CurrentRateFormDtoMapper.class);

    /**
     * Maps the microservice cryptocurrency current rate data to the CurrentRateFormDto object.
     *
     * @param currentRateDto        The client current rate.
     * @param requestTime           The current rate requestTime.
     * @param currentRateDateFormat The date format for the current rate.
     * @param desiredPrecision      The desired precision for scaling the rate.
     * @param desiredScale          The desired scale for scaling the rate.
     * @param locale                The locale.
     * @return The mapped CurrentRateFormDto object.
     */
    public static CurrentRateFormDto mapClientToFormDto(CurrentRateDto currentRateDto,
                                                        LocalDateTime requestTime,
                                                        String currentRateDateFormat,
                                                        int desiredPrecision, int desiredScale, Locale locale) {

        BigDecimal rate = currentRateDto.getCurrentRate();

        // Get the scaled rate with desired precision and scale using RateScaleUtil
        String displayRate = RateScaleUtil.getScaledRate(rate, desiredPrecision, desiredScale,
                currentRateDto.getCoinSymbol(), NumberFormat.getCurrencyInstance(locale));

        // Format the rate update date using the provided date format and locale
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(currentRateDateFormat).withLocale(locale);

        if (currentRateDto.getRateDateTime() != null) {

            // Format the rate update date if available
            String formattedUpdateDateTime = dateFormatter.format(currentRateDto.getRateDateTime());
            String formattedUpdateRequestDateTime = dateFormatter.format(requestTime);
            return new CurrentRateFormDto(displayRate, formattedUpdateDateTime, formattedUpdateRequestDateTime);
        } else {

            logger.info("Rate time is not available for coin: " +
                    currentRateDto.getCoinCode() + " and currency: " + currentRateDto.getCurrencyCode());

            // If rate update date is not available, create CurrentRateFormDto with an empty update date
            return new CurrentRateFormDto(displayRate, "", "");
        }
    }
}
