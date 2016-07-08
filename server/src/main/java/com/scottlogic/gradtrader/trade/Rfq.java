package com.scottlogic.gradtrader.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rfq {

    private final String userId;
    private final String portfolioId;

    private final String pair;
    private final long quantity;
    private final Direction direction;
    private final long indicativePrice;

    @JsonCreator
    public Rfq(@JsonProperty("userId") String userId, @JsonProperty("portfolioId") String portfolioId,
            @JsonProperty("pair") String pair, @JsonProperty("quantity") long quantity,
            @JsonProperty("direction") String direction, @JsonProperty("indicativePrice") long indicativePrice) {
        super();
        this.userId = userId;
        this.portfolioId = portfolioId;
        this.pair = pair;
        this.quantity = quantity;
        this.direction = Direction.valueOf(direction);
        this.indicativePrice = indicativePrice;
    }

    public String getUserId() {
        return userId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public String getPair() {
        return pair;
    }

    public long getQuantity() {
        return quantity;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getIndicativePrice() {
        return indicativePrice;
    }

}
