package com.scottlogic.gradtrader.config;

import io.dropwizard.Configuration;

import java.util.Map;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottlogic.gradtrader.pair.Pair;

public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    private Map<String, Pair> validPairs;

    @NotEmpty
    private Map<String, Long> testPrices;

    @Min(1L)
    private long priceFeedMillis;

    @Min(1L)
    private long clientBroadcastMillis;

    @JsonProperty
    public Map<String, Pair> getValidPairs() {
        return validPairs;
    }

    @JsonProperty
    public Map<String, Long> getTestPrices() {
        return testPrices;
    }

    @JsonProperty
    public long getPriceFeedMillis() {
        return priceFeedMillis;
    }

    @JsonProperty
    public long getClientBroadcastMillis() {
        return clientBroadcastMillis;
    }

}
