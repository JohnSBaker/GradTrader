package com.scottlogic.gradtrader;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottlogic.gradtrader.price.history.PriceHistoryStore;

public class App extends Application<GradTraderConfiguration> {

    Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

      App app = new App();
    	app.run(args);
    }

    @Override
    public void initialize(Bootstrap<GradTraderConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));

    }

    @Override
    public void run(GradTraderConfiguration configuration, Environment environment) {

      Injector injector = Guice.createInjector(new AppInjector());

      logger.debug("Create PriceHistory store...");

      //PriceHistoryStore priceHistoryStore = injector.getInstance(PriceHistoryStore.class);
      
      PairResource pairResource = new PairResource(configuration.getValidPairs());

      final PairsHealthCheck pairsHealthCheck = new PairsHealthCheck(configuration.getValidPairs());
      environment.healthChecks().register("pairs", pairsHealthCheck);
      
      environment.jersey().register(pairResource);

      runWebSocketServer(environment);
    }

    private void runWebSocketServer(Environment environment){
	    AtmosphereServlet servlet = new AtmosphereServlet();
	    servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "gradtrader.websockets");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

	    ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("WSService", servlet);
	    servletHolder.addMapping("/api/ws/*");

    }

}
