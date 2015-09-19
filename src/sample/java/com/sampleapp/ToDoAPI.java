package com.sampleapp;

import com.sampleapp.resource.AuthResource;
import com.sampleapp.resource.ToDoItemResource;
import com.sampleapp.resource.UserResource;
import com.servicewizard.ServiceWizard;
import com.servicewizard.resource.DefaultHTMLDocumentationResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class ToDoAPI extends Application<ToDoConfiguration> {
	public static void main(String... args) throws Exception {
		new ToDoAPI().run(args);
	}

	@Override
	public String getName() {
		return "ToDo API";
	}

	@Override
	public void initialize(Bootstrap<ToDoConfiguration> bootstrap) {
	}

	@Override
	public void run(ToDoConfiguration configuration, Environment environment) throws Exception {
		// bind resources
		environment.jersey().register(new ToDoItemResource());
		environment.jersey().register(new AuthResource());
		environment.jersey().register(new UserResource());
		environment.jersey().register(new DefaultHTMLDocumentationResource(getName(), "htp://localhost:8080", "com.sampleapp"));

		// output docs to files
		ServiceWizard.process(configuration.getServiceWizard(), "com.sampleapp");
	}
}
