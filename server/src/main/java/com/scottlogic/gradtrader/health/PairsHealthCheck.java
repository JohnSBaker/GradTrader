package com.scottlogic.gradtrader.health;

import java.util.Map;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.scottlogic.gradtrader.pair.Pair;

public class PairsHealthCheck extends HealthCheck {
    private final Map<String, Pair> pairs;

    @Inject
    public PairsHealthCheck(Map<String, Pair> pairs) {
        this.pairs = pairs;
    }

    @Override
    protected Result check() throws Exception {
        if (pairs == null || pairs.size() == 0) {
            return Result
                    .unhealthy("at least one currency pair must be defined");
        }
        return Result.healthy();
    }
}
