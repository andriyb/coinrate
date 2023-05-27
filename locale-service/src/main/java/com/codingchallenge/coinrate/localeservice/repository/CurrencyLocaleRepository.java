package com.codingchallenge.coinrate.localeservice.repository;

import com.codingchallenge.coinrate.localeservice.domain.CurrencyLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyLocaleRepository extends JpaRepository<CurrencyLocale, Integer> {

    @Query("SELECT cl FROM CurrencyLocale cl WHERE cl.countryCode = :code ORDER BY CASE WHEN cl.defaultCurrency = false THEN 0 ELSE 1 END DESC")
    List<CurrencyLocale> getByCountryCode(@Param("code") String code);
}
