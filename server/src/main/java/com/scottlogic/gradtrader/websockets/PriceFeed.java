package com.scottlogic.gradtrader.websockets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.PathParam;
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

@WebSocketHandlerService(path = "/api/ws/price/{pair}/", broadcaster = SimpleBroadcaster.class)
public class PriceFeed extends WebSocketHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(PriceFeed.class);

    private final ObjectMapper mapper = new ObjectMapper();
    
    private static double priceStart = 0.0;
   
	Map<String,PriceGenerator> priceGenerators = new LinkedHashMap<String, PriceGenerator>(); 
	
	public PriceFeed(){
		super();
		logger.debug("PriceFeed created");
	}
    
	@Inject
	private BroadcasterFactory factory;
    
    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
    	try{
    		String pathInfo = webSocket.resource().getRequest().getPathInfo();
    		String[] decodedPath = pathInfo.split("/");
    		String pair = decodedPath[decodedPath.length - 1];
    		
	    	Broadcaster broadcaster = factory.lookup("/api/ws/price/" + pair +"/", true); 
	        webSocket.resource().setBroadcaster(broadcaster);
	        
	        PriceGenerator pg = priceGenerators.get(pair);
	        if (pg==null){
	        	logger.debug("Generate prices for " + pair);
	        	priceStart = priceStart + 1.0;
	        	pg = new IncrementalPriceGenerator(pair, priceStart, priceStart + 1.0, 0.2, 0.02);
	        	priceGenerators.put(pair, pg);
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
