package com.scottlogic.gradtrader.price;

import org.atmosphere.cpr.BroadcasterFactory;

import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.websockets.WebSocketClient;

public interface PriceService {

	public void subscribe(WebSocketClient client, String pair) throws SubscriptionException;
	public void unsubscribe(WebSocketClient client, String pair) throws SubscriptionException;

	public void setFactory(BroadcasterFactory factory);

	
}
