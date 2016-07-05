package com.scottlogic.gradtrader.pair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pair {

    private final String name;
    private final int decimals;

    @JsonCreator
    public Pair(@JsonProperty("name") String name,
            @JsonProperty("decimals") int decimals) {
        super();
        this.name = name;
        this.decimals = decimals;
    }

    public String getName() {
        return name;
    }

    public int getDecimals() {
        return decimals;
    }

}
