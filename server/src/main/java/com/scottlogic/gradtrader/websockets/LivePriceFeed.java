package com.scottlogic.gradtrader.websockets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandlerAdapter;
import org.atmosphere.websocket.WebSocketProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import com.scottlogic.gradtrader.AppInjector;
import com.scottlogic.gradtrader.GradTraderConfiguration;
import com.scottlogic.gradtrader.price.IncrementalPriceGenerator;
import com.scottlogic.gradtrader.price.PriceGenerator;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;

@WebSocketHandlerService(path = "/api/ws/price/live/", broadcaster = SimpleBroadcaster.class)
public class LivePriceFeed extends WebSocketHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(LivePriceFeed.class);

    private final ObjectMapper mapper = new ObjectMapper();
    
    private static double priceStart = 0.0;
    private static int nextClientId = 1;
       
	private static List<String> validPairs = null; 
	
	// websocket uuid to client id
	private Map<String, WebSocketClient> clients = new LinkedHashMap<String, WebSocketClient>();
	
	@Inject
	private BroadcasterFactory factory;
	
	@Inject
	private PriceHistoryStore store;
	
	@Inject
	GradTraderConfiguration configuration;
	
	public LivePriceFeed(){
		super();
		logger.debug("PriceFeed created");
	}
	
	private void init(){
		logger.debug("Init valid pairs");
		validPairs = AppInjector.getConfiguration().getValidPairs();
		
		for (String pair: validPairs){
			try{
	    		Broadcaster bc = factory.get(pair);
	    		priceStart = priceStart + 1.0;
	    		
	    		PriceGenerator pg = new IncrementalPriceGenerator(pair, priceStart, priceStart + 1.0, 0.2, 0.02, 0.0001);
	    		pg.setPriceHistoryStore(store);
	        	bc.scheduleFixedBroadcast(pg, 0, 5, TimeUnit.SECONDS);
	        	logger.debug("Added broadcaster for {}", pair);
			} catch (Exception e){
				logger.debug("Exception creating broadcaster ", e);
			}
		}		
	}
    
    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
		if (validPairs==null){
			init();
		}
    	try{
    		String newClientId = "client-"+nextClientId++;
    		clients.put(webSocket.uuid(), new WebSocketClient(webSocket, newClientId));
    		
    		ClientActionResponse response = new ClientActionResponse(newClientId, "connect", "", "success", "");
    		webSocket.resource().write(mapper.writeValueAsString(response));
    		
    		logger.debug("Socket open, broadcaster factory: {}", webSocket.resource().getAtmosphereConfig().getBroadcasterFactory());
    		logger.debug("Socket open, object factory: {}", webSocket.resource().getAtmosphereConfig().getInitParameter("org.atmosphere.cpr.objectFactory"));
    		
    	} catch (Exception e){
    		logger.error("PriceFeed error", e);
    	}
    }

    private void sendResponse(WebSocket webSocket, String clientId, String action, 
    		String subject, String result, String message){
		ClientActionResponse response = new ClientActionResponse(clientId, action, subject, result, message);
		try {
			webSocket.resource().write(mapper.writeValueAsString(response));
		} catch (JsonProcessingException e) {
			logger.error("Json error: ", e);
		}
    	
    }
    
    public void onTextMessage(WebSocket webSocket, String message) throws IOException {
    	ClientActionRequest clientActionRequest = mapper.readValue(message, ClientActionRequest.class);
    	String subject = clientActionRequest.getSubject();
    	WebSocketClient client = clients.get(webSocket.uuid());
    	logger.debug("From {} on socket {}: {}", client.getClientId(), webSocket.uuid(), message);
    	String clientId = client.getClientId();
		String action = clientActionRequest.getAction();
    	if (client!=null && clientId.equals(clientActionRequest.getClientId())){
        	switch (action){
    			case ClientActionRequest.SUBSCRIBE_PRICE:
    				if (validPairs.contains(subject)){
    					client.subscribe(subject);
    					try{
    						Broadcaster bc = factory.lookup(subject);
    						if (bc.getAtmosphereResources().contains(webSocket.resource())){
	        		    		sendResponse(webSocket, clientId, action, subject, "error", "already subscribed");
		    					logger.debug("{} already subscribed to {}", clientId, subject);    							
    						} else {
	    						bc.addAtmosphereResource(webSocket.resource());
	        		    		sendResponse(webSocket, clientId, action, subject, "success", "");
		    					logger.debug("{} subscribed to {}", clientId, subject);
    						}
    					} catch (Exception e){
        		    		sendResponse(webSocket, clientId, action, subject, "error", "");
    						logger.debug("Subscription error: {}", e);
    					}
    				} else {
    		    		sendResponse(webSocket, clientId, action, subject, "error", "invalid pair");
    					logger.debug("Invalid pair: {}", subject);
    				}
    				break;
    			case ClientActionRequest.UNSUBSCRIBE_PRICE:
    				if (validPairs.contains(subject)){
    					client.unsubscribe(subject);
    					try{
    						Broadcaster bc = factory.lookup(subject);
    						if (!bc.getAtmosphereResources().contains(webSocket.resource())){
            		    		sendResponse(webSocket, clientId, action, subject, "error", "not subscribed");
    	    					logger.debug("{} not subscribed to {}", client.getClientId(), subject);
    						} else {
    							bc.removeAtmosphereResource(webSocket.resource());
            		    		sendResponse(webSocket, clientId, action, subject, "success", "");    							
    	    					logger.debug("{} unsubscribed to {}", client.getClientId(), subject);
    						}
    					} catch (Exception e){
        		    		sendResponse(webSocket, clientId, action, subject, "error", "");
    						logger.debug("Unsubscription error: {}", e);
    					}
    				} else {
    		    		sendResponse(webSocket, clientId, action, subject, "error", "invalid pair");
    					logger.debug("Invalid pair: {}", subject);
    				}
    				break;
    			case ClientActionRequest.SUBSCRIBE_TRADES:
    				break;
    			case ClientActionRequest.UNSUBSCRIBE_TRADES:
    				break;
    			default:
		    		sendResponse(webSocket, clientId, action, subject, "error", "unknown action");
    				logger.debug("Unknown client action: {}", clientActionRequest.getAction());
        	}    		
    	} else {
    		sendResponse(webSocket, clientId, action, subject, "error", "invalid clientId");
    		logger.debug("Client id mismatch: {} given on socket for {}", clientActionRequest.getClientId(), client.getClientId());
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
