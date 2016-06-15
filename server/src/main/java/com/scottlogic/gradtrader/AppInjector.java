package com.scottlogic.gradtrader;

import com.google.inject.AbstractModule;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;
import com.scottlogic.gradtrader.price.history.RedisPriceHistoryStore;

public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        //bind(PriceGenerator.class).to(IncrementalPriceGenerator.class);
        bind(PriceHistoryStore.class).to(RedisPriceHistoryStore.class);
    }

}
