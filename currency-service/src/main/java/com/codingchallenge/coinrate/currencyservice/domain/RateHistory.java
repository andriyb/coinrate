package com.codingchallenge.coinrate.currencyservice.domain;



import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rate_history")
public class RateHistory {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "currencyCode")
    private String currencyCode;

    @Column(name = "coinCode")
    private String coinCode;

    @Column(name = "coinName")
    private String coinName;

    @Column(name = "coinSymbol")
    private String coinSymbol;

    @Column(name = "rate")
    private BigDecimal rate;

    public RateHistory() {
    }

    public RateHistory(Long id, String currencyCode, String coinCode, String coinSymbol, String coinName, BigDecimal rate, LocalDate date) {
        this.id = id;
        this.currencyCode = currencyCode;
        this.coinCode = coinCode;
        this.coinName = coinName;
        this.coinSymbol = coinSymbol;
        this.rate = rate;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currency) {
        this.currencyCode = currency;
    }

    public String getCoinCode() {
        return coinCode;
    }

    public void setCoinCode(String coinCode) {
        this.coinCode = coinCode;
    }

    public String getCoinName() {
        return coinName;
    }

    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal price) {
        this.rate = price;
    }
}
