package com.scottlogic.gradtrader.websockets;

import java.io.IOException;

import javax.ws.rs.Path;

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


@Path("/")
@AtmosphereHandlerService(path = "/api/ws/echo",
        broadcasterCache = UUIDBroadcasterCache.class,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class,
                BroadcastOnPostAtmosphereInterceptor.class,
                TrackMessageSizeInterceptor.class,
                HeartbeatInterceptor.class
        })
public class WebSocketService extends OnMessage<String> {
    private final ObjectMapper mapper = new ObjectMapper();

	PriceGenerator pg = new IncrementalPriceGenerator("gbpusd", 1.0, 2.0, 0.2, 0.02);
	
    @Override
    public void onMessage(AtmosphereResponse response, String message) throws IOException {
        //response.write(mapper.writeValueAsString(mapper.readValue(message, User.class)));
    	response.write(message);
    	try {
			response.write(mapper.writeValueAsString(pg.call()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
