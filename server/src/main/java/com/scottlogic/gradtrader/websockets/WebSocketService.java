package com.scottlogic.gradtrader.websockets;

import java.io.IOException;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.apache.log4j.Logger;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.handler.OnMessage;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scottlogic.gradtrader.price.IncrementalPriceGenerator;
import com.scottlogic.gradtrader.price.PriceGenerator;


@Path("/api/ws/echo")
@AtmosphereHandlerService(path = "/",
        broadcasterCache = UUIDBroadcasterCache.class,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class,
                BroadcastOnPostAtmosphereInterceptor.class,
                TrackMessageSizeInterceptor.class,
                HeartbeatInterceptor.class
        })
public class WebSocketService extends OnMessage<String> {

    Logger logger = Logger.getLogger(WebSocketService.class);
    
	@Override
    public void onMessage(AtmosphereResponse response, String message) throws IOException {
    	response.write("Echo: " + message);
    }
}
