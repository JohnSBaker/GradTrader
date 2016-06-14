package com.scottlogic.gradtrader.websockets;

import java.util.LinkedHashSet;
import java.util.Set;

import org.atmosphere.websocket.WebSocket;

public class WebSocketClient {

	private final String wsUuid;
	private final String clientId;
	
	private Set<String> pairSubscriptions = new LinkedHashSet<String>();
	
	public WebSocketClient(WebSocket webSocket, String clientId){
		this.wsUuid = webSocket.uuid();
		this.clientId = clientId;
	}
	
	public String getWsUuid() {
		return wsUuid;
	}

	public String getClientId() {
		return clientId;
	}

	public void subscribe(String pair){
		pairSubscriptions.add(pair);
	}

	public void unsubscribe(String pair){
		pairSubscriptions.remove(pair);
	}
	
	public boolean isSubscribed(String pair){
		return pairSubscriptions.contains(pair);
	}
	
}
