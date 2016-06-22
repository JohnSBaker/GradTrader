package com.scottlogic.gradtrader;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.dropwizard.setup.Environment;

import java.util.List;

class GradTraderModule extends AbstractModule {

    private final GradTraderConfiguration configuration;
    private final Environment environment;

    GradTraderModule(final GradTraderConfiguration configuration, final Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    @Override
    protected void configure() {

    }

    @Provides
    public GradTraderConfiguration provideConfiguration() {
        return configuration;
    }

    @Provides
    public List<String> provideValidPairs() {
        return configuration.getValidPairs();
    }

    @Provides
    public Environment provideEnvironment() {
        return environment;
    }
}
