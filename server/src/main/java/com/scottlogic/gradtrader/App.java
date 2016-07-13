package com.scottlogic.gradtrader;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.FrameworkConfig;
import org.atmosphere.guice.AtmosphereGuiceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.scottlogic.gradtrader.config.DeviceTypeServletFilter;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.config.GradTraderModule;
import com.scottlogic.gradtrader.health.PairsHealthCheck;
import com.scottlogic.gradtrader.pair.PairResource;
import com.scottlogic.gradtrader.price.history.PriceHistoryResource;
import com.scottlogic.gradtrader.trade.RfqResource;
import com.scottlogic.gradtrader.trade.TradeResource;

public class App extends Application<GradTraderConfiguration> {

    Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(final Bootstrap<GradTraderConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(final GradTraderConfiguration configuration, final Environment environment) {

        final GradTraderModule gradTraderModule = new GradTraderModule(configuration, environment);

        final Injector injector = gradTraderModule.getInjector();

        environment.jersey().register(injector.getInstance(PairResource.class));
        environment.jersey().register(injector.getInstance(PriceHistoryResource.class));
        environment.healthChecks().register("pairs", injector.getInstance(PairsHealthCheck.class));
        environment.jersey().register(injector.getInstance(RfqResource.class));
        environment.jersey().register(injector.getInstance(TradeResource.class));

        runWebSocketServer(environment);

        environment.servlets().addFilter("DeviceTypeServletFilter", new DeviceTypeServletFilter())
                .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/");

        logger.debug("GradTrader started");
    }

    private void runWebSocketServer(final Environment environment) {
        final AtmosphereGuiceServlet servlet = new AtmosphereGuiceServlet();

        servlet.framework().addInitParameter(ApplicationConfig.OBJECT_FACTORY,
                "com.scottlogic.gradtrader.config.ObjectFactory");
        servlet.framework().addInitParameter(ApplicationConfig.BROADCASTER_CLASS,
                "com.scottlogic.gradtrader.websockets.ClientBroadcaster");
        servlet.framework().addInitParameter(FrameworkConfig.JERSEY_SCANNING_PACKAGE, "gradtrader.websockets");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
        servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

        final ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("WSService", servlet);

        servletHolder.addMapping("/api/ws/*");
    }
}
