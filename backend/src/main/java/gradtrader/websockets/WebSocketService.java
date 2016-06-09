package gradtrader.websockets;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResponse;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.BroadcastOnPostAtmosphereInterceptor;
import org.atmosphere.client.TrackMessageSizeInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;
import org.atmosphere.handler.OnMessage;
import javax.ws.rs.Path;
import java.io.IOException;


@Path("/")
@AtmosphereHandlerService(path = "/api/ws/echo",
        broadcasterCache = UUIDBroadcasterCache.class,
        interceptors = {AtmosphereResourceLifecycleInterceptor.class,
                BroadcastOnPostAtmosphereInterceptor.class,
                TrackMessageSizeInterceptor.class,
                HeartbeatInterceptor.class
        })
public class WebSocketService extends OnMessage<String> {
    //private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void onMessage(AtmosphereResponse response, String message) throws IOException {
        //response.write(mapper.writeValueAsString(mapper.readValue(message, User.class)));
    	response.write("Echo: " + message);
    }
}
