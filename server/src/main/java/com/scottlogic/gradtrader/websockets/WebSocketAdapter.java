package com.scottlogic.gradtrader.websockets;

import java.io.IOException;

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
import com.google.inject.Inject;
import com.scottlogic.gradtrader.SubscriptionException;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;

@WebSocketHandlerService(path = "/api/ws/", broadcaster = SimpleBroadcaster.class)
public class WebSocketAdapter extends WebSocketHandlerAdapter {

    Logger logger = LoggerFactory.getLogger(WebSocketAdapter.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private GradTraderConfiguration configuration;
    @Inject
    private BroadcasterFactory factory;

    @Override
    public void onOpen(WebSocket webSocket) throws IOException {
        try {
            ClientActionResponse response = new ClientActionResponse("connect", "", "success", "");
            webSocket.resource().write(mapper.writeValueAsString(response));

        } catch (Exception e) {
            logger.error("PriceFeed error: ", e);
        }
    }

    private ClientBroadcaster getBroadcaster(final WebSocket webSocket) {
        ClientBroadcaster clientBroadcaster = null;
        final SimpleBroadcaster broadcaster = factory.lookup(webSocket);

        if (broadcaster != null) {
            if (broadcaster instanceof ClientBroadcaster) {
                clientBroadcaster = (ClientBroadcaster) broadcaster;
            } else {
                factory.remove(webSocket);
            }
        }

        if (clientBroadcaster == null) {
            clientBroadcaster = (ClientBroadcaster) factory.get(ClientBroadcaster.class, webSocket);
            clientBroadcaster.addAtmosphereResource(webSocket.resource());
            clientBroadcaster.start(configuration.getClientBroadcastMillis());
            logger.debug("Broadcasting to new client {}");
        }

        return clientBroadcaster;
    }

    public void onTextMessage(WebSocket webSocket, String message) throws IOException {
        ClientBroadcaster clientBroadcaster = getBroadcaster(webSocket);
        ClientActionRequest clientActionRequest = mapper.readValue(message, ClientActionRequest.class);
        String subject = clientActionRequest.getSubject();
        logger.debug("{}: {}", webSocket.uuid(), message);
        String action = clientActionRequest.getAction();
        try {
            switch (action) {
            case ClientActionRequest.SUBSCRIBE_PRICE:
                subscribePrice(webSocket, clientBroadcaster, subject);
                break;
            case ClientActionRequest.UNSUBSCRIBE_PRICE:
                unsubscribePrice(webSocket, clientBroadcaster, subject);
                break;
            case ClientActionRequest.SUBSCRIBE_TRADE:
                subscribeTrade(webSocket, clientBroadcaster, subject);
                break;
            case ClientActionRequest.UNSUBSCRIBE_TRADE:
                unsubscribeTrade(webSocket, clientBroadcaster, subject);
                break;
            default:
                sendResponse(webSocket, action, subject, "error", "Unknown action");
            }
        } catch (SubscriptionException e) {
            sendResponse(webSocket, action, subject, "error", e.getMessage());
        }
    }

    private void subscribePrice(WebSocket webSocket, ClientBroadcaster clientBroadcaster, String pairId)
            throws SubscriptionException {
        clientBroadcaster.subscribePrices(pairId);
        sendResponse(webSocket, ClientActionRequest.SUBSCRIBE_PRICE, pairId, "success", "");
    }

    private void unsubscribePrice(WebSocket webSocket, ClientBroadcaster clientBroadcaster, String pairId)
            throws SubscriptionException {
        clientBroadcaster.unsubscribePrices(pairId);
        sendResponse(webSocket, ClientActionRequest.UNSUBSCRIBE_PRICE, pairId, "success", "");
    }

    private void subscribeTrade(WebSocket webSocket, ClientBroadcaster clientBroadcaster, String userId)
            throws SubscriptionException {
        clientBroadcaster.subscribeTrades(userId);
        sendResponse(webSocket, ClientActionRequest.SUBSCRIBE_TRADE, userId, "success", "");
    }

    private void unsubscribeTrade(WebSocket webSocket, ClientBroadcaster clientBroadcaster, String userId)
            throws SubscriptionException {
        clientBroadcaster.unsubscribeTrades(userId);
        sendResponse(webSocket, ClientActionRequest.UNSUBSCRIBE_TRADE, userId, "success", "");
    }

    private void sendResponse(WebSocket webSocket, String action, String subject, String result, String message) {
        ClientActionResponse response = new ClientActionResponse(action, subject, result, message);
        try {
            String json = mapper.writeValueAsString(response);
            logger.debug(json);
            webSocket.resource().write(json);
        } catch (JsonProcessingException e) {
            logger.error("Json error: ", e);
        }

    }

    public void onClose(WebSocket webSocket) {
        logger.debug("Client closed socket {} ", webSocket.uuid());
    }

    public void onError(WebSocket webSocket, WebSocketProcessor.WebSocketException t) {
        logger.debug("Client on socket {}: error: ", webSocket.uuid(), t);
    }

}
