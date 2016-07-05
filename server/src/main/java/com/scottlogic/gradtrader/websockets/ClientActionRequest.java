package com.scottlogic.gradtrader.websockets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientActionRequest {

    public final static String SUBSCRIBE_PRICE = "subscribePrice";
    public final static String UNSUBSCRIBE_PRICE = "unsubscribePrice";
    public final static String SUBSCRIBE_TRADES = "subscribeTrades";
    public final static String UNSUBSCRIBE_TRADES = "unsubscribeTrades";

    private final String action;
    private final String subject;

    @JsonCreator
    public ClientActionRequest(@JsonProperty("action") String action,
            @JsonProperty("subject") String subject) {
        this.action = action;
        this.subject = subject;
    }

    public String getAction() {
        return action;
    }

    public String getSubject() {
        return subject;
    }

}