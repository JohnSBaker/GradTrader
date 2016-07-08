package com.scottlogic.gradtrader.health;

import java.util.Map;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.pair.Pair;

public class PairsHealthCheck extends HealthCheck {

    @Inject
    private Map<String, Pair> pairs;

    @Override
    protected Result check() throws Exception {
        if (pairs == null || pairs.size() == 0) {
            return Result.unhealthy("at least one currency pair must be defined");
        }
        return Result.healthy();
    }
}
