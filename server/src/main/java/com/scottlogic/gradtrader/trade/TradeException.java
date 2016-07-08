package com.scottlogic.gradtrader.trade;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.scottlogic.gradtrader.GradTraderErrorEntity;

public class TradeException extends WebApplicationException {

    private static final long serialVersionUID = 1L;

    public TradeException(String errorMessage) {
        super(Response.status(Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(new GradTraderErrorEntity(errorMessage)).build());
    }

}
