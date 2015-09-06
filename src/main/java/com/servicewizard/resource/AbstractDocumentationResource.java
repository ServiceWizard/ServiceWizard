package com.servicewizard.resource;

import java.io.IOException;
import java.io.StringWriter;

import com.servicewizard.locator.ServiceLocator;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.TemplateTransformer;

public abstract class AbstractDocumentationResource {

	protected String getDocumentation() {
		return documentation;
	}

	public AbstractDocumentationResource(String apiName, TemplateTransformer transformer, ServiceLocator locator) {
		cacheDocumentation(transformer, buildModel(apiName, locator));
	}

	protected ServiceModel buildModel(final String apiName, final ServiceLocator locator) {
		return new ServiceModel() {
			{
				setApiName(apiName);
				getServices().addAll(locator.locate());
			}
		};
	}

	private void cacheDocumentation(TemplateTransformer transformer, ServiceModel serviceModel) {
		StringWriter writer = new StringWriter();

		try {
			transformer.transform(serviceModel, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		documentation = writer.toString();
	}

	private String documentation;
}
