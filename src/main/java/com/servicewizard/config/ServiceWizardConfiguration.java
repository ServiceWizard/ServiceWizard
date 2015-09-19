package com.servicewizard.config;

import java.util.LinkedList;
import java.util.List;

/**
 * Bean-compliant configuration class, usable by dropwizard.
 * 
 * @author forana
 */
public class ServiceWizardConfiguration {
	/**
	 * Base URL at which the described API will be running.
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * The name of the API.
	 */
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	/**
	 * File path for overview text used for every transformer.
	 */
	public String getOverviewPath() {
		return overviewPath;
	}

	public void setOverviewPath(String overviewPath) {
		this.overviewPath = overviewPath;
	}

	/**
	 * Configurations for each transformer to be used.
	 */
	public List<TransformerConfiguration> getTransformers() {
		return transformers;
	}

	public void setTransformers(List<TransformerConfiguration> transformers) {
		this.transformers = transformers;
	}

	private String baseUrl;

	private String apiName;

	private String overviewPath;

	private List<TransformerConfiguration> transformers = new LinkedList<>();
}
