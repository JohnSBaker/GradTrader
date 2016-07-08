package com.scottlogic.gradtrader.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.scottlogic.gradtrader.websockets.WebSocketData;

public class Trade implements WebSocketData {

    private long tradeId;
    private long time;
    private Quote quote;

    @JsonCreator
    public Trade(long tradeId, long time, Quote quote) {
        super();
        this.tradeId = tradeId;
        this.time = time;
        this.quote = quote;
    }

    public long getTradeId() {
        return tradeId;
    }

    public long getTime() {
        return time;
    }

    public Quote getQuote() {
        return quote;
    }

}
