package com.scottlogic.gradtrader.pair;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pair {

    private final String name;
    private final Integer decimals;

    @JsonCreator
    public Pair(@JsonProperty("name") String name, @JsonProperty("decimals") Integer decimals) {
        super();
        this.name = name;
        this.decimals = decimals;
    }

    public String getName() {
        return name;
    }

    public Integer getDecimals() {
        return decimals;
    }

}
