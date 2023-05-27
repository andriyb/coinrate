package com.codingchallenge.coinrate.localeservice.service.mapper;

import com.codingchallenge.coinrate.localeservice.domain.CurrencyLocale;
import com.codingchallenge.coinrate.localeservice.service.dto.CurrencyLocaleDto;

public class CurrencyLocaleDtoMapper {

    public CurrencyLocaleDto mapEntityToDto(CurrencyLocale entity) {
        CurrencyLocaleDto dto =
                new CurrencyLocaleDto(entity.getCountryCode(), entity.getLangCode(),
                        entity.getCurrencyCode(), entity.isDefaultCurrency());

        return dto;
    }

}
