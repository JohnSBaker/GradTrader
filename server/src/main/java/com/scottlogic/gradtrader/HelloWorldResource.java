package com.scottlogic.gradtrader;

import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;
import javax.inject.Inject;

@Path("/helloworld")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;
    private final GreetingService greetingService;

    public HelloWorldResource(GreetingService gs, String template, String defaultName) {
        this.greetingService = gs;
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Greeting sayHello(@QueryParam("name") Optional<String> name) {
        final String value = greetingService.createGreeting(name.or(defaultName));
        return new Greeting(counter.incrementAndGet(), value);
    }
}