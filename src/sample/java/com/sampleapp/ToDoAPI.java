package com.sampleapp;

import com.sampleapp.resource.AuthResource;
import com.sampleapp.resource.DocsResource;
import com.sampleapp.resource.ToDoItemResource;
import com.sampleapp.resource.UserResource;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ToDoAPI extends Application<Configuration> {
    public static void main(String... args) throws Exception {
        new ToDoAPI().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
        environment.jersey().register(new ToDoItemResource());
        environment.jersey().register(new AuthResource());
		environment.jersey().register(new UserResource());
		environment.jersey().register(new DocsResource());
    }
}
