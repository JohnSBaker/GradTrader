package com.scottlogic.gradtrader;

import com.google.inject.AbstractModule;

public class AppInjector extends AbstractModule {

    @Override
    protected void configure() {
        //bind the service to implementation class
        //bind(PriceGenerator.class).to(IncrementalPriceGenerator.class);
    }

}
