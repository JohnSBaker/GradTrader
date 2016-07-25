package com.scottlogic.gradtrader.price.source;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PriceConfiguration {

    private final String id;
    private final int basePrice;
    private final int broadcastFrequencyMillis;

    @JsonCreator
    public PriceConfiguration(@JsonProperty("id") final String id,
                              @JsonProperty("basePrice") final int basePrice,
                              @JsonProperty("broadcastFrequencyMillis") final int broadcastFrequencyMillis) {
        super();
        this.id = id;
        this.basePrice = basePrice;
        this.broadcastFrequencyMillis = broadcastFrequencyMillis;
    }

    public String getId() {
        return id;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getBroadcastFrequencyMillis() {
        return broadcastFrequencyMillis;
    }
}
