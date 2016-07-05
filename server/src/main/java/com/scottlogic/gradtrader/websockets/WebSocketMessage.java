package com.scottlogic.gradtrader.websockets;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;

public class WebSocketMessage {

    private final String action;
    private final Collection<? extends WebSocketData> data;

    @JsonCreator
    public WebSocketMessage(String action,
            Collection<? extends WebSocketData> data) {
        super();
        this.action = action;
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public Collection<? extends WebSocketData> getData() {
        return data;
    }

}
