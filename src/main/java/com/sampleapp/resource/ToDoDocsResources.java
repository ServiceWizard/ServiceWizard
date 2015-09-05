package com.sampleapp.resource;

import com.servicewizard.ServiceLocator;
import com.servicewizard.ServiceWizardMethod;
import com.servicewizard.ServiceWizardService;
import com.servicewizard.resource.DocumentationResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/docs")
@ServiceWizardService(name="Documentation")
public class ToDoDocsResources extends DocumentationResource {

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

    public ToDoDocsResources() {
        super(new ServiceLocator("com.sampleapp.resource"));
    }
}
