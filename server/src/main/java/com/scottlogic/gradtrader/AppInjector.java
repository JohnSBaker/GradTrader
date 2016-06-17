package com.scottlogic.gradtrader;

import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcasterFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottlogic.gradtrader.price.PriceGenerationService;
import com.scottlogic.gradtrader.price.PriceService;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;
import com.scottlogic.gradtrader.price.history.RedisPriceHistoryStore;

public class AppInjector extends AbstractModule {

	private final static AppInjector appInjector = new AppInjector();
	private final static Injector injector = Guice.createInjector(appInjector);
	private static GradTraderConfiguration configuration;
	
	public static Injector getInjector(){
		return injector;
	}

	public static AbstractModule getModule(){
		return appInjector;
	}
	
	public static void setConfiguration(GradTraderConfiguration config){
		configuration = config;
	}
	
	public static GradTraderConfiguration getConfiguration(){
		return configuration;
	}
	
	private AppInjector(){
		super();
	}
	
    @Override
    protected void configure() {
        //bind interfaces to implementation classes
        //bind(PriceGenerator.class).to(IncrementalPriceGenerator.class);
        //bind(BroadcasterFactory.class).to(DefaultBroadcasterFactory.class);
        bind(PriceHistoryStore.class).to(RedisPriceHistoryStore.class);
        bind(PriceService.class).to(PriceGenerationService.class);
        //bind(BroadcasterFactory.class).to(DefaultBroadcasterFactory.class);
    }

}
