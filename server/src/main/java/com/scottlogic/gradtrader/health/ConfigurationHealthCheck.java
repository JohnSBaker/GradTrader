package com.scottlogic.gradtrader.health;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;

public class ConfigurationHealthCheck extends HealthCheck {

    Logger logger = LoggerFactory.getLogger(ConfigurationHealthCheck.class);

    @Inject
    private GradTraderConfiguration config;

    @Override
    protected Result check() throws Exception {
        logger.debug("check config health check");
        Set<String> pairs = config.getValidPairs().keySet();
        if (pairs == null || pairs.size() == 0) {
            return Result.unhealthy("at least one currency pair must be defined");
        }
        return Result.healthy();
    }

}
