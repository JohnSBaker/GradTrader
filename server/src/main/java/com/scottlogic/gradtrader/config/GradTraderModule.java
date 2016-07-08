package com.scottlogic.gradtrader.config;

import io.dropwizard.setup.Environment;

import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.scottlogic.gradtrader.ScheduledExecutor;
import com.scottlogic.gradtrader.pair.Pair;
import com.scottlogic.gradtrader.price.feed.PriceFeedFactory;
import com.scottlogic.gradtrader.price.feed.PriceFeedFactoryImpl;
import com.scottlogic.gradtrader.price.source.FakePriceSourceFactory;
import com.scottlogic.gradtrader.price.source.PriceSourceFactory;
import com.scottlogic.gradtrader.trade.QuoteEngine;
import com.scottlogic.gradtrader.trade.QuoteEngineImpl;
import com.scottlogic.gradtrader.trade.TradeManager;
import com.scottlogic.gradtrader.trade.TradeManagerImpl;

@Singleton
public class GradTraderModule extends AbstractModule {

    Logger logger = LoggerFactory.getLogger(GradTraderModule.class);

    private static GradTraderModule gradTraderModule;
    private final GradTraderConfiguration configuration;
    private final Environment environment;

    private TradeManager tradeManager;

    private final Injector injector;

    public GradTraderModule(final GradTraderConfiguration configuration, final Environment environment) {
        super();
        this.configuration = configuration;
        this.environment = environment;
        injector = Guice.createInjector(this);
        gradTraderModule = this;
    }

    public Injector getInjector() {
        return injector;
    }

    public static GradTraderModule getInstance() {
        return gradTraderModule;
    }

    @Override
    protected void configure() {
        bind(PriceFeedFactory.class).to(PriceFeedFactoryImpl.class);
        bind(PriceSourceFactory.class).to(FakePriceSourceFactory.class);
        bind(ScheduledThreadPoolExecutor.class).to(ScheduledExecutor.class);
        bind(QuoteEngine.class).to(QuoteEngineImpl.class);
    }

    @Provides
    public GradTraderConfiguration provideConfiguration() {
        return configuration;
    }

    @Provides
    public TradeManager provideTradeManager() {
        if (tradeManager == null) {
            tradeManager = new TradeManagerImpl();
            injector.injectMembers(tradeManager);
        }
        return tradeManager;
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
