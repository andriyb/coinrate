package com.codingchallenge.coinrate.rategateway.unit.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.HistorySettingsDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.HistorySettingsFormDto;
import com.codingchallenge.coinrate.rategateway.service.mapper.HistorySettingsFormDtoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("HistorySettingsFormDtoMapper tests")
class HistorySettingsFormDtoMapperTest {

    @Test
    @DisplayName("Mapping client history settings to form DTO with specified days count limit and default days count.")
    void testMapClientToFormDto_GermanFormatSettings() {
        // Prepare test data
        HistorySettingsDto historySettingsDto = new HistorySettingsDto(30, 7);

        // Perform the mapping
        HistorySettingsFormDto formDto = HistorySettingsFormDtoMapper.mapClientToFormDto(historySettingsDto);

        // Verify the mapping results
        assertEquals(30, formDto.getHistoryDaysCountLimit());
        assertEquals(7, formDto.getHistoryDaysCountDefault());
    }
}