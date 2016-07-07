package com.scottlogic.gradtrader.pair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pair {

    private final String id;
    private final int decimals;

    @JsonCreator
    public Pair(
            @JsonProperty("id") String id,
            @JsonProperty("decimals") int decimals) {
        super();
        this.id = id;
        this.decimals = decimals;
    }

    public String getId() {
        return id;
    }

    public int getDecimals() {
        return decimals;
    }

}
