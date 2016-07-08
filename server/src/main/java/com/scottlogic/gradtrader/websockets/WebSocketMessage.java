package com.scottlogic.gradtrader.websockets;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class WebSocketMessage {

    private final String action;
    private final Collection<? extends WebSocketData> data;

    @JsonCreator
    public WebSocketMessage(String action, Collection<? extends WebSocketData> data) {
        super();
        this.action = action;
        this.data = data;
    }

    @JsonCreator
    public WebSocketMessage(String action, WebSocketData datum) {
        super();
        this.action = action;
        List<WebSocketData> data = new LinkedList<WebSocketData>();
        data.add(datum);
        this.data = data;
    }

    public String getAction() {
        return action;
    }

    public Collection<? extends WebSocketData> getData() {
        return data;
    }

}
