package com.scottlogic.gradtrader;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.scottlogic.gradtrader.config.GradTraderConfiguration;
import com.scottlogic.gradtrader.config.GradTraderModule;
import com.scottlogic.gradtrader.health.PairsHealthCheck;
import com.scottlogic.gradtrader.pair.PairResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<GradTraderConfiguration> {

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<GradTraderConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(GradTraderConfiguration configuration, Environment environment) {

        final Injector injector = Guice.createInjector(new GradTraderModule(configuration, environment));

        environment.jersey().register(injector.getInstance(PairResource.class));
        environment.healthChecks().register("pairs", injector.getInstance(PairsHealthCheck.class));
    }

}
