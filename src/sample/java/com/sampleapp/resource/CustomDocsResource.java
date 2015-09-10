package com.sampleapp.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.resource.AbstractDocumentationResource;
import com.servicewizard.transformer.TemplateTransformer;

@Path("/docs2")
public class CustomDocsResource extends AbstractDocumentationResource {
	private static final String VERY_BASIC_TEMPLATE = "Resources:\n<#list services as service>* ${service.name}\n</#list>";

	public CustomDocsResource(String apiName, String packagePath) {
		super(apiName, new TemplateTransformer(VERY_BASIC_TEMPLATE), new JerseyResourceLocator(packagePath));
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCustomText() {
		return getDocumentation();
	}
}
