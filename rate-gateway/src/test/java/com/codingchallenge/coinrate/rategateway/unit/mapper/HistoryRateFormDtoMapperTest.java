package com.codingchallenge.coinrate.rategateway.unit.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.HistoryRateDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.HistoryRateFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.HistoryRateFormDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HistoryRateFormDtoMapper tests")
class HistoryRateFormDtoMapperTest {

    private static final Locale GERMAN_LOCALE = new Locale("de", "DE");
    private static final String CURRENT_RATE_DATE_FORMAT = "dd.MM.yyyy";
    private static final int DESIRED_PRECISION = 2;
    private static final int DESIRED_SCALE = 4;

    @Test
    @DisplayName("Mapping client history rates to form DTOs with German format settings")
    void testMapClientToFormDto_GermanFormatSettings() {
        // Prepare test data
        List<HistoryRateDto> historyRates = Arrays.asList(
                        new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                                new BigDecimal("27974.166955638662"), LocalDate.parse("2023-06-25")) ,

                        new HistoryRateDto("bitcoin", "EUR", "Bitcoin", "btc",
                                new BigDecimal("0.000027933"), LocalDate.parse("2023-06-26"))
        );

        // Perform the mapping
        List<HistoryRateFormDto> formDtos = HistoryRateFormDtoMapper.mapClientToFormDto(historyRates,
                CURRENT_RATE_DATE_FORMAT, DESIRED_PRECISION, DESIRED_SCALE, GERMAN_LOCALE);

        // Verify the mapping results
        assertEquals(2, formDtos.size());

        HistoryRateFormDto historyDto1 = formDtos.get(0);
        assertEquals("25.06.2023", historyDto1.getFormattedRateDate());
        assertEquals("1 btc = 27.974,17\u00a0€", historyDto1.getFormattedRate());

        HistoryRateFormDto historyDto2 = formDtos.get(1);
        assertEquals("26.06.2023", historyDto2.getFormattedRateDate());
        assertEquals("100 btc = 0,00\u00a0€", historyDto2.getFormattedRate());
    }
}