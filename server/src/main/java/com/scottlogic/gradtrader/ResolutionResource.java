package com.scottlogic.gradtrader;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;

@Path("/resolution")
@Produces(MediaType.APPLICATION_JSON)
public class ResolutionResource {
	
    Logger logger = LoggerFactory.getLogger(ResolutionResource.class);

    private final List<Integer> resolutions;

    public ResolutionResource() {
    	this.resolutions = AppInjector.getConfiguration().getPriceHistoryResolutions();
    }

    @GET
    @Timed
    public Resolutions getResolutions() {
        return new Resolutions(resolutions);
    }
}
