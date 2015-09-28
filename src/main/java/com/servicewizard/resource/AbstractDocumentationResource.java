package com.servicewizard.resource;

import java.io.IOException;
import java.io.StringWriter;

import com.servicewizard.Log;
import com.servicewizard.locator.ServiceLocator;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.TemplateTransformer;

public abstract class AbstractDocumentationResource {

	protected String getDocumentation() {
		return documentation;
	}

	protected ServiceModel buildModel(final String apiName, final ServiceLocator locator) {
		return new ServiceModel() {
			{
				setApiName(apiName);
				getServices().addAll(locator.locate());
			}
		};
	}

	private void cacheDocumentation(TemplateTransformer transformer, String urlBase, ServiceModel serviceModel) {
		StringWriter writer = new StringWriter();

		try {
			transformer.transform(urlBase, serviceModel, writer);
		} catch (IOException e) {
			Log.error(e);
		}

		documentation = writer.toString();
	}

	public AbstractDocumentationResource(String apiName, String urlBase, TemplateTransformer transformer, ServiceLocator locator) {
		cacheDocumentation(transformer, urlBase, buildModel(apiName, locator));
	}

	private String documentation;
}
