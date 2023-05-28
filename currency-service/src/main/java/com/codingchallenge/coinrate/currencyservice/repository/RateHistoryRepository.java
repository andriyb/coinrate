package com.codingchallenge.coinrate.currencyservice.repository;

import com.codingchallenge.coinrate.currencyservice.domain.RateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public interface RateHistoryRepository extends JpaRepository<RateHistory, Long> {

    @Query("SELECT c FROM RateHistory c WHERE c.date = :date")
    List<RateHistory> findAllByDate(@Param("date") LocalDate date);

    @Query("SELECT c FROM RateHistory c WHERE c.coinCode = :coinCode AND c.currencyCode = :currencyCode " +
            "AND c.date BETWEEN :startDate AND :endDate ORDER BY c.date DESC")
    List<RateHistory> findAllByDateRange(
            @Param("coinCode") String coinCode, @Param("currencyCode") String currencyCode,
            @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM RateHistory c WHERE c.date = :date")
    void deleteAllByDate(@Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("DELETE FROM RateHistory")
    void deleteAll();

}
