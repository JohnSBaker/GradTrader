package com.scottlogic.gradtrader.trade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.price.PriceException;

@Path("/quote/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RfqResource {

    @Inject
    private TradeManager tradeManager;

    @POST
    @Timed
    public Quote getQuote(Rfq rfq) throws PriceException {
        return tradeManager.requestQuote(rfq);
    }

}
