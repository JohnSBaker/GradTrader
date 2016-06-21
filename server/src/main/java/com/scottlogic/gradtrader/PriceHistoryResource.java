package com.scottlogic.gradtrader;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;

@Path("{pair}/price")
@Produces(MediaType.APPLICATION_JSON)
public class PriceHistoryResource {

    Logger logger = LoggerFactory.getLogger(PriceHistoryResource.class);
	PriceHistoryStore store;

    @Inject
    public PriceHistoryResource(PriceHistoryStore store) {
    	this.store = store;
    }

    @GET
    @Timed
    public PriceHistory getPriceHistory(@PathParam("pair") String pair,
    		@QueryParam("resolution") Integer resolution,
    		@QueryParam("from") Long from,
    		@QueryParam("to") Long to) {
    	
    	return store.getHistory(pair, resolution, from, to);
    }
	
}
