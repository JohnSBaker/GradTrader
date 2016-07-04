package com.scottlogic.gradtrader.config;

import com.scottlogic.gradtrader.pair.Pair;
import io.dropwizard.setup.Environment;

import java.util.Map;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class GradTraderModule extends AbstractModule {

    private static GradTraderModule gradTraderModule;
    private final GradTraderConfiguration configuration;
    private final Environment environment;

    public GradTraderModule(final GradTraderConfiguration configuration,
                            final Environment environment) {
        this.configuration = configuration;
        this.environment = environment;
    }

    public static void setModule(GradTraderModule gradTraderModule) {
        GradTraderModule.gradTraderModule = gradTraderModule;
    }

    public static GradTraderModule getModule() {
        return GradTraderModule.gradTraderModule;
    }

    @Override
    protected void configure() {

    }

    @Provides
    public GradTraderConfiguration provideConfiguration() {
        return configuration;
    }

    @Provides
    public Map<String, Pair> provideValidPairs() {
        return configuration.getValidPairs();
    }

    @Provides
    public Environment provideEnvironment() {
        return environment;
    }
}
