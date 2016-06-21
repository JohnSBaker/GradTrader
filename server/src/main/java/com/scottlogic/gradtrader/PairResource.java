package com.scottlogic.gradtrader;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

@Path("/pair")
@Produces(MediaType.APPLICATION_JSON)
public class PairResource {
	
    Logger logger = LoggerFactory.getLogger(PairResource.class);

    private final List<String> pairs;

    public PairResource() {
    	this.pairs = AppInjector.getConfiguration().getValidPairs();
    }

    @GET
    @Timed
    public Pairs getPairs() {
        return new Pairs(pairs);
    }
}
