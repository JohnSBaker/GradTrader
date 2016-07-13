package com.scottlogic.gradtrader.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.scottlogic.gradtrader.websockets.WebSocketData;

public class Trade implements WebSocketData {

    private long tradeId;
    private long timestamp;
    private String userId;
    private String portfolioId;
    private String pairId;
    private long quantity;
    private Direction direction;
    private long price;

    @JsonCreator
    public Trade(long tradeId, long timestamp, Quote quote) {
        super();
        this.tradeId = tradeId;
        this.timestamp = timestamp;
        this.userId = quote.getUserId();
        this.portfolioId = quote.getPortfolioId();
        this.pairId = quote.getPairId();
        this.quantity = quote.getQuantity();
        this.direction = quote.getDirection();
        this.price = quote.getPrice();
    }

    public String getUserId() {
        return userId;
    }

    public String getPortfolioId() {
        return portfolioId;
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

    public long getTradeId() {
        return tradeId;
    }

    public long getTimestamp() {
        return timestamp;
    }

}
