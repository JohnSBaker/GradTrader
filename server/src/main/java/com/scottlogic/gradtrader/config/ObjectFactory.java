package com.scottlogic.gradtrader.config;

import org.atmosphere.guice.GuiceObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectFactory extends GuiceObjectFactory {

    Logger logger = LoggerFactory.getLogger(ObjectFactory.class);

    public ObjectFactory() {
        super();
        allowInjectionOf(GradTraderModule.getInstance());
    }

}
