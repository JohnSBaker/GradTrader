package com.scottlogic.gradtrader.price.history;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.price.PriceException;
import com.scottlogic.gradtrader.price.source.PriceSource;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

@Path("/price/{pairId}")
@Produces(MediaType.APPLICATION_JSON)
public class PriceHistoryResource {

    @Inject
    PriceSourceFactory priceSourceFactory;

    @GET
    @Timed
    public Collection<PriceHistory> getPriceHistory(@PathParam("pairId") String pairId,
            @QueryParam("resolution") Long resolution, @QueryParam("from") Long from, @QueryParam("to") Long to)
            throws PriceException {
        return priceSourceFactory.getPriceSource(pairId).getPriceHistory(from, to, resolution);
    }

}
