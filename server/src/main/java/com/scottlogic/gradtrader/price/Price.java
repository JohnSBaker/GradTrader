package com.scottlogic.gradtrader.price;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scottlogic.gradtrader.websockets.WebSocketData;

public class Price implements WebSocketData {

    private final String pair;
    private final long time;
    private final long bid;
    private final long ask;
    private final long mid;

    @JsonCreator
    public Price(String pair, long time, long bid, long ask) {
        this.pair = pair;
        this.time = time;
        this.bid = bid;
        this.ask = ask;
        this.mid = (bid + ask) / 2;
    }

    public String getPair() {
        return pair;
    }

    public long getTime() {
        return time;
    }

    public long getBid() {
        return bid;
    }

    public long getAsk() {
        return ask;
    }

    @JsonIgnore
    public long getMid() {
        return mid;
    }

}
