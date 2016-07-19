package com.scottlogic.gradtrader.price.history;

public class PriceHistory {

    private long timestamp;
    private long open;
    private long close;
    private long low;
    private long high;

    public PriceHistory(long timestamp, long open, long close, long low, long high) {
        this.timestamp = timestamp;
        this.open = open;
        this.close = close;
        this.low = low;
        this.high = high;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public long getOpen() {
        return open;
    }

    public long getClose() {
        return close;
    }

    public long getLow() {
        return low;
    }

    public long getHigh() {
        return high;
    }

}
