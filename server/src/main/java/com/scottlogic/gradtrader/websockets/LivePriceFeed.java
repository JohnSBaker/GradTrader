package com.scottlogic.gradtrader.websockets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandlerAdapter;
import org.atmosphere.websocket.WebSocketProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.price.PriceService;

@WebSocketHandlerService(path = "/api/ws/price/live/", broadcaster = SimpleBroadcaster.class)
public class LivePriceFeed extends WebSocketHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(LivePriceFeed.class);

    private final ObjectMapper mapper = new ObjectMapper();
    
    private static int nextClientId = 1;
       	
	// websocket uuid to client 
	private Map<String, WebSocketClient> clients = new LinkedHashMap<String, WebSocketClient>();
			
	private PriceService priceService;
	
	@Inject
	public LivePriceFeed(PriceService priceService, BroadcasterFactory factory){
		super();
		this.priceService = priceService;
		priceService.setFactory(factory);
		logger.debug("PriceFeed created");
	}
	    
    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
    	try{
    		String newClientId = "client-"+nextClientId++;
    		clients.put(webSocket.uuid(), new WebSocketClient(webSocket, newClientId));
    		
    		ClientActionResponse response = new ClientActionResponse(newClientId, "connect", "", "success", "");
    		webSocket.resource().write(mapper.writeValueAsString(response));
    		
    	} catch (Exception e){
    		logger.error("PriceFeed error", e);
    	}
    }
    
    public void onTextMessage(WebSocket webSocket, String message) throws IOException {
    	ClientActionRequest clientActionRequest = mapper.readValue(message, ClientActionRequest.class);
    	String subject = clientActionRequest.getSubject();
    	WebSocketClient client = clients.get(webSocket.uuid());
    	logger.debug("{}: {}", client.getClientId(), message);
    	String clientId = client.getClientId();
		String action = clientActionRequest.getAction();
    	if (client!=null && clientId.equals(clientActionRequest.getClientId())){
        	switch (action){
    			case ClientActionRequest.SUBSCRIBE_PRICE:
    				try{
    					priceService.subscribe(client, subject);
    		    		sendResponse(webSocket, clientId, action, subject, "success", "");
    				} catch (SubscriptionException e){
    					sendResponse(webSocket, clientId, action, subject, "error", e.getMessage());
    				}
    				break;
    			case ClientActionRequest.UNSUBSCRIBE_PRICE:
    				try{
    					priceService.unsubscribe(client, subject);
    		    		sendResponse(webSocket, clientId, action, subject, "success", "");
    				} catch (SubscriptionException e){
    					sendResponse(webSocket, clientId, action, subject, "error", e.getMessage());
    				}
    				break;
    			case ClientActionRequest.SUBSCRIBE_TRADES:
    				break;
    			case ClientActionRequest.UNSUBSCRIBE_TRADES:
    				break;
    			default:
		    		sendResponse(webSocket, clientId, action, subject, "error", "Unknown action");
        	}    		
    	} else {
    		sendResponse(webSocket, clientId, action, subject, "error", "Invalid clientId");
    	}
    }
    
    private void sendResponse(WebSocket webSocket, String clientId, String action, 
    		String subject, String result, String message){
		ClientActionResponse response = new ClientActionResponse(clientId, action, subject, result, message);
		try {
			String json = mapper.writeValueAsString(response);
			logger.debug(json);
			webSocket.resource().write(json);
		} catch (JsonProcessingException e) {
			logger.error("Json error: ", e);
		}
    	
    }
    public void onClose(WebSocket webSocket){
    	logger.debug("Client {} closed socket {} ", clients.get(webSocket.uuid()).getClientId(), webSocket.uuid());
    	//TODO: remove from client map
    }
    
    public void onError(WebSocket webSocket,  WebSocketProcessor.WebSocketException t){
    	logger.debug("Client {} on socket {}: error: ", clients.get(webSocket.uuid()).getClientId(), webSocket.uuid(), t);
    }
    
}
