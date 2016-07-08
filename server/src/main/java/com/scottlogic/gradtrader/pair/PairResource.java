package com.scottlogic.gradtrader.pair;

import java.util.Collection;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

@Path("/pair")
@Produces(MediaType.APPLICATION_JSON)
public class PairResource {

    @Inject
    private Map<String, Pair> pairs;

    @GET
    @Timed
    public Collection<Pair> getPairs() {
        return pairs.values();
    }
}
