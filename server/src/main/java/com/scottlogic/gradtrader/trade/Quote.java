package com.scottlogic.gradtrader.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Quote {

    private final String userId;
    private final String portfolioId;

    private final long quoteId;

    private final long time;
    private final long expires;
    private final String pairId;
    private final long quantity;
    private final Direction direction;
    private final long price;

    private boolean accepted = false;

    @JsonCreator
    public Quote(@JsonProperty("userId") String userId, @JsonProperty("portfolioId") String portfolioId,
            @JsonProperty("quoteId") long quoteId, @JsonProperty("time") long time,
            @JsonProperty("expiry") long expiry, @JsonProperty("pairId") String pairId,
            @JsonProperty("quantity") long quantity, @JsonProperty("direction") Direction direction,
            @JsonProperty("price") long price) {
        super();
        this.userId = userId;
        this.portfolioId = portfolioId;
        this.quoteId = quoteId;
        this.time = time;
        this.expires = time + expiry;
        this.pairId = pairId;
        this.quantity = quantity;
        this.direction = direction;
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public long getQuoteId() {
        return quoteId;
    }

    public long getTime() {
        return time;
    }

    public long getExpires() {
        return expires;
    }

    public String getPairId() {
        return pairId;
    }

    public long getQuantity() {
        return quantity;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getPrice() {
        return price;
    }

    @JsonIgnore
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted() {
        accepted = true;
    }

}
