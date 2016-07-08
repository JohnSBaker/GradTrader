package com.scottlogic.gradtrader.trade;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

@Path("/trade/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TradeResource {

    @Inject
    private TradeManager tradeManager;

    @GET
    @Path("/{userId}")
    @Timed
    public Collection<Trade> getTrades(@PathParam("userId") String userId) throws TradeException {
        return tradeManager.getTrades(userId);
    }

    @POST
    @Timed
    public Trade placeTrade(Quote quote) throws TradeException {
        return tradeManager.placeTrade(quote);
    }

}
