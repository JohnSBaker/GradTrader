package com.scottlogic.gradtrader.config;

import io.dropwizard.setup.Environment;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.scottlogic.gradtrader.ScheduledExecutor;
import com.scottlogic.gradtrader.pair.Pair;
import com.scottlogic.gradtrader.price.feed.PriceFeedFactory;
import com.scottlogic.gradtrader.price.feed.PriceFeedFactoryImpl;
import com.scottlogic.gradtrader.price.source.FakePriceSourceFactory;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;

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
        bind(PriceFeedFactory.class).to(PriceFeedFactoryImpl.class);
        bind(PriceSourceFactory.class).to(FakePriceSourceFactory.class);
        bind(ScheduledThreadPoolExecutor.class).to(ScheduledExecutor.class);
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
