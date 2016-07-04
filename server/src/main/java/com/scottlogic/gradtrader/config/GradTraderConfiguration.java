package com.scottlogic.gradtrader.config;

import com.scottlogic.gradtrader.pair.Pair;
import io.dropwizard.Configuration;

import java.util.Map;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    private Map<String, Pair> validPairs;

    @JsonProperty
    public Map<String, Pair> getValidPairs() {
        return validPairs;
    }

}
