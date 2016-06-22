package com.scottlogic.gradtrader;

import java.util.List;

import com.codahale.metrics.health.HealthCheck;

import javax.inject.Inject;

class PairsHealthCheck extends HealthCheck {
    private final List<String> pairs;

    @Inject
    public PairsHealthCheck(List<String> pairs) {
        this.pairs = pairs;
    }

    @Override
    protected Result check() throws Exception {
        if (pairs == null || pairs.size() == 0) {
            return Result.unhealthy("at least one currency pair must be defined");
        }
        return Result.healthy();
    }
}
