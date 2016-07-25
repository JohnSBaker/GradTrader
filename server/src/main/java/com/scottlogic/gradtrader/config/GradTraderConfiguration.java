package com.scottlogic.gradtrader.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.scottlogic.gradtrader.pair.Pair;
import com.scottlogic.gradtrader.price.source.PriceConfiguration;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.Map;

public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    private Map<String, Pair> validPairs;

    @NotEmpty
    private Map<String, PriceConfiguration> priceConfigurations;

    @Min(1L)
    private long clientBroadcastMillis;

    @JsonProperty
    public Map<String, Pair> getValidPairs() {
        return validPairs;
    }

    @JsonProperty
    public Map<String, PriceConfiguration> getPriceConfigurations() {
        return priceConfigurations;
    }

    @JsonProperty
    public long getClientBroadcastMillis() {
        return clientBroadcastMillis;
    }
}
