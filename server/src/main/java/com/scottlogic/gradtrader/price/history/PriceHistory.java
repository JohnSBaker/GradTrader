package com.scottlogic.gradtrader.price.history;

public class PriceHistory {

    private Long start;
    private Long end;
    private Long open;
    private Long close;
    private Long min;
    private Long max;
    private Integer scale;

    public PriceHistory(Long start, Long end, Long open, Long close, Long min, Long max) {
        super();
        this.start = start;
        this.end = end;
        this.open = open;
        this.close = close;
        this.min = min;
        this.max = max;
    }

    public Long getStart() {
        return start;
    }

    public Long getEnd() {
        return end;
    }

    public Long getOpen() {
        return open;
    }

    public Long getClose() {
        return close;
    }

    public Long getMin() {
        return min;
    }

    public Long getMax() {
        return max;
    }

}
