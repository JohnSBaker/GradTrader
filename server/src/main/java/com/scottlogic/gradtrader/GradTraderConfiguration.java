package com.scottlogic.gradtrader;

import io.dropwizard.Configuration;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.inject.Singleton;

@Singleton
public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    private List<String> validPairs;

    @NotEmpty
    private List<Integer> priceHistoryResolutions;
    
    @JsonProperty
	public List<String> getValidPairs() {
		return validPairs;
	}

    @JsonProperty
	public void setValidPairs(List<String> validPairs) {
		this.validPairs = validPairs;
	}

    @JsonProperty
	public List<Integer> getPriceHistoryResolutions() {
		return priceHistoryResolutions;
	}

    @JsonProperty
	public void setPriceHistoryResolutions(List<Integer> resolutions) {
		this.priceHistoryResolutions = resolutions;
	}
    
}
