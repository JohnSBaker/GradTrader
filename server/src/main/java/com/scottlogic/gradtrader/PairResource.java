package com.scottlogic.gradtrader;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/pair")
@Produces(MediaType.APPLICATION_JSON)
public class PairResource {

    private final List<String> pairs;

    @Inject
    public PairResource(List<String> pairs) {
        this.pairs = pairs;
    }

    @GET
    @Timed
    public List<String> getPairs() {
        return pairs;
    }
}
