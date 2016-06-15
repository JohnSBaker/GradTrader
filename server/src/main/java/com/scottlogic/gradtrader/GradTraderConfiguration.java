package com.scottlogic.gradtrader;

import io.dropwizard.Configuration;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    public List<String> validPairs;
    
    @JsonProperty
	public List<String> getValidPairs() {
		return validPairs;
	}

    @JsonProperty
	public void setValidPairs(List<String> validPairs) {
		this.validPairs = validPairs;
	}
    
}
