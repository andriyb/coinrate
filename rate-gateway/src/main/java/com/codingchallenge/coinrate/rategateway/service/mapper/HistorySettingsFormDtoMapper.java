package com.codingchallenge.coinrate.rategateway.service.mapper;

import com.codingchallenge.coinrate.rategateway.client.dto.HistorySettingsDto;
import com.codingchallenge.coinrate.rategateway.service.dto.form.HistorySettingsFormDto;

/**
 * Mapper class for converting cryptocurrency history settings between the microservice and form data transfer objects.
 */
public class HistorySettingsFormDtoMapper {

    /**
     * Maps a HistorySettingsDto object to a HistorySettingsFormDto object.
     *
     * @param historySettingsDto The HistorySettingsDto object to be mapped.
     * @return The mapped HistorySettingsFormDto object.
     */
    public static HistorySettingsFormDto mapClientToFormDto(HistorySettingsDto historySettingsDto) {


        return new HistorySettingsFormDto(historySettingsDto.getHistoryDaysCountLimit(),
                historySettingsDto.getHistoryDaysCountDefault());
    }
}
