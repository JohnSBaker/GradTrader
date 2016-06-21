package com.scottlogic.gradtrader;

import io.dropwizard.Configuration;

import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Singleton
public class GradTraderConfiguration extends Configuration {

    @NotEmpty
    private List<String> validPairs;

    @NotEmpty
    private List<Integer> priceHistoryResolutions;
    
    @NotEmpty
    private Map<String, Double> testPrices;
    
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

    @JsonProperty
	public Map<String, Double> getTestPrices() {
		return testPrices;
	}

    @JsonProperty
	public void setTestPrices(Map<String, Double> testPrices) {
		this.testPrices = testPrices;
	}
    
    
    
}
