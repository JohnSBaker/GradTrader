package com.scottlogic.gradtrader;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;

public class ConfigurationHealthCheck extends HealthCheck { 

    Logger logger = LoggerFactory.getLogger(ConfigurationHealthCheck.class);
	
	private GradTraderConfiguration config;
	
    @Inject
	public ConfigurationHealthCheck(GradTraderConfiguration config){
    	logger.debug("create config health check");
		this.config = config;
	}
    
    @Override
    protected Result check() throws Exception {
    	logger.debug("check config health check");
    	List<String> pairs = config.getValidPairs();
        if (pairs==null || pairs.size()==0) {
            return Result.unhealthy("at least one currency pair must be defined");
        }
        return Result.healthy();
    }

}
