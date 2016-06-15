package com.scottlogic.gradtrader;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;

public class App extends Application<GradTraderConfiguration> {


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

      //Injector injector = Guice.createInjector(new AppInjector());

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
