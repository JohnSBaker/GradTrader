package com.scottlogic.gradtrader.websockets;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.xml.crypto.Data;

import org.atmosphere.config.service.WebSocketHandlerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.util.SimpleBroadcaster;
import org.atmosphere.websocket.WebSocket;
import org.atmosphere.websocket.WebSocketHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.gradtrader.price.IncrementalPriceGenerator;
import com.scottlogic.gradtrader.price.PriceGenerator;

@WebSocketHandlerService(path = "/api/ws/price/gbpusd/", broadcaster = SimpleBroadcaster.class)
public class PriceFeed extends WebSocketHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(PriceFeed.class);

    private final ObjectMapper mapper = new ObjectMapper();
   
	PriceGenerator pg; // or map of 1 per pair?
	
	public PriceFeed(){
		super();
		logger.debug("PriceFeed created");
	}
    
	@Inject
	private BroadcasterFactory factory;
    
    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
    	String pair = "gbpusd";
    	try{
	    	logger.debug("webSocket: {}", webSocket);   
	    	logger.debug("factory: {}", factory);  	
	    	Broadcaster broadcaster = factory.lookup("/api/ws/price/gbpusd/", true); 
	    	logger.debug("broadcaster: {}", broadcaster);	    	
	        webSocket.resource().setBroadcaster(broadcaster);
	        logger.debug("resource: {}", webSocket.resource().toString());
	        if (pg==null){
	        	logger.debug("Generate prices for " + pair);
	        	pg = new IncrementalPriceGenerator(pair, 1.0, 2.0, 0.2, 0.02);
	        	Future<Object> future = broadcaster.scheduleFixedBroadcast(pg, 0, 5, TimeUnit.SECONDS);
	        } else {
	        	logger.debug("Already generating prices for " + pair);	        	
	        }
    	} catch (Exception e){
    		logger.error("PriceFeed error", e);
    	}
    }

    public void onTextMessage(WebSocket webSocket, String message) throws IOException {
        AtmosphereResource r = webSocket.resource();
        Broadcaster b = r.getBroadcaster();
        b.broadcast(mapper.writeValueAsString(mapper.readValue(message, Data.class)));
    }
    
}
