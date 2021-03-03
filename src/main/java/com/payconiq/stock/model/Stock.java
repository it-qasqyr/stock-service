package com.payconiq.stock.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode
@ToString
// @Immutable
public class Stock {

    private final Long id;
    private final String name;
    private final Double currentPrice;
    private final Timestamp lastUpdate;

    public Stock(Long id, String name, Double currentPrice, Timestamp lastUpdate) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.lastUpdate = lastUpdate;
    }
}
