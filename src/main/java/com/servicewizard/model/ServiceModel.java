package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;

public class ServiceModel {

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public List<Service> getServices() {
		return services;
	}

	public ServiceModel() {
		services = new LinkedList<>();
	}

	private String apiName;

	private String overview;

	private final List<Service> services;
}
