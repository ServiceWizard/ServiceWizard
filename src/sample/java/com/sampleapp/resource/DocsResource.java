package com.sampleapp.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicewizard.ServiceWizardMethod;
import com.servicewizard.ServiceWizardService;
import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.resource.DocumentationResource;

@Path("/docs")
@ServiceWizardService(name="Documentation")
public class DocsResource extends DocumentationResource {

    @GET
    @Path("/all")
    @Produces(MediaType.TEXT_HTML)
    @Override
    @ServiceWizardMethod(
            title="API Documentation",
            description="Gets the documentation of the ToDO application's API"
    )
    public String getDocumentation() {
        return super.getDocumentation();
    }

    public DocsResource() {
		super(new JerseyResourceLocator("com.sampleapp.resource"));
    }
}
