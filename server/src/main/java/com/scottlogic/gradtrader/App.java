package com.scottlogic.gradtrader;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.guice.AtmosphereGuiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.config.GradTraderModule;
import com.scottlogic.gradtrader.health.PairsHealthCheck;
import com.scottlogic.gradtrader.pair.PairResource;
import com.scottlogic.gradtrader.price.history.PriceHistoryResource;
import com.scottlogic.gradtrader.trade.RfqResource;
import com.scottlogic.gradtrader.trade.TradeResource;

public class App extends Application<GradTraderConfiguration> {

    Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<GradTraderConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(GradTraderConfiguration configuration, Environment environment) {

        GradTraderModule gradTraderModule = new GradTraderModule(configuration, environment);

        final Injector injector = gradTraderModule.getInjector();

        environment.jersey().register(injector.getInstance(PairResource.class));
        environment.jersey().register(injector.getInstance(PriceHistoryResource.class));
        environment.healthChecks().register("pairs", injector.getInstance(PairsHealthCheck.class));
        environment.jersey().register(injector.getInstance(RfqResource.class));
        environment.jersey().register(injector.getInstance(TradeResource.class));

        runWebSocketServer(environment);

        logger.debug("GradTrader started");
    }

    private void runWebSocketServer(Environment environment) {
        AtmosphereGuiceServlet servlet = new AtmosphereGuiceServlet();

        servlet.framework().addInitParameter("org.atmosphere.cpr.objectFactory",
                "com.scottlogic.gradtrader.config.ObjectFactory");

        servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "gradtrader.websockets");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

        ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("WSService", servlet);

        servletHolder.addMapping("/api/ws/*");
    }

}
