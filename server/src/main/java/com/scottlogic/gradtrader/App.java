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

      Injector injector = AppInjector.getInjector();
      AppInjector.setConfiguration(configuration);

      environment.healthChecks().register("configuration", injector.getInstance(ConfigurationHealthCheck.class));

      environment.jersey().register(injector.getInstance(PairResource.class));
      environment.jersey().register(injector.getInstance(PriceHistoryResource.class));

      runWebSocketServer(configuration, environment);
    }

    private void runWebSocketServer(GradTraderConfiguration configuration, Environment environment){
	    AtmosphereGuiceServlet servlet = new AtmosphereGuiceServlet(); 
	    
	    servlet.framework().addInitParameter("org.atmosphere.cpr.objectFactory", "com.scottlogic.gradtrader.ObjectFactory"); 
	    
	    servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "gradtrader.websockets");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");
	    
	    ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("WSService", servlet);   	    
	    
	    servletHolder.addMapping("/api/ws/*");
    }

}
