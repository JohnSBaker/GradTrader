package com.scottlogic.gradtrader;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletRegistration;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.cpr.DefaultBroadcaster;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottlogic.gradtrader.price.IncrementalPriceGenerator;
import com.scottlogic.gradtrader.price.PriceGenerator;

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

      Injector injector = Guice.createInjector(new AppInjector());

      GreetingService greetingService = injector.getInstance(GreetingService.class);

      HelloWorldResource resource = new HelloWorldResource(
        greetingService,
        configuration.getTemplate(),
        configuration.getDefaultName()
      );

      final TemplateHealthCheck healthCheck =
             new TemplateHealthCheck(configuration.getTemplate());
      environment.healthChecks().register("template", healthCheck);
      environment.jersey().register(resource);

      runWebSocketServer(environment);
    }

    private void runWebSocketServer(Environment environment){
	    AtmosphereServlet servlet = new AtmosphereServlet();
	    servlet.framework().addInitParameter("com.sun.jersey.config.property.packages", "gradtrader.websockets");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_CONTENT_TYPE, "application/json");
	    servlet.framework().addInitParameter(ApplicationConfig.WEBSOCKET_SUPPORT, "true");

	    ServletRegistration.Dynamic servletHolder = environment.servlets().addServlet("WSService", servlet);
	    servletHolder.addMapping("/api/ws/*");

	    //TODO: guice
	    //PriceGenerator gbpusdPriceGenerator = new IncrementalPriceGenerator("GBPUSD", 1.0, 2.0, 0.2, 0.02);

	    //BroadcasterFactory factory = DefaultBroadcasterFactory.getDefault();
	    //Broadcaster broadcaster = factory.lookup("/api/ws/price/gbpusd/");

	    //DefaultBroadcaster broadcaster = new DefaultBroadcaster();
	    //Future<Object> future = broadcaster.scheduleFixedBroadcast(gbpusdPriceGenerator, 0, 10, TimeUnit.SECONDS);
	    //"/api/ws/price/gbpusd",


    }

}
