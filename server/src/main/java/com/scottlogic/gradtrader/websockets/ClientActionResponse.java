package com.scottlogic.gradtrader.websockets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientActionResponse {

    public final static String SUBSCRIBE_PRICE = "subscribePrice";
    public final static String UNSUBSCRIBE_PRICE = "unsubscribePrice";
    public final static String SUBSCRIBE_TRADES = "subscribeTrades";
    public final static String UNSUBSCRIBE_TRADES = "unsubscribeTrades";

    private final String action;
    private final String subject;
    private final String result;
    private final String message;

    @JsonCreator
    public ClientActionResponse(@JsonProperty("action") String action,
            @JsonProperty("subject") String subject,
            @JsonProperty("result") String result,
            @JsonProperty("message") String message) {
        this.action = action;
        this.subject = subject;
        this.result = result;
        this.message = message;
    }

    public String getAction() {
        return action;
    }

    public String getSubject() {
        return subject;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

}