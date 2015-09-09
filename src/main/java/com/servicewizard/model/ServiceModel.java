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

	public String getIntroText() {
		return introText;
	}

	public void setIntroText(String introText) {
		this.introText = introText;
	}

	public List<Service> getServices() {
		return services;
	}

	public ServiceModel() {
		services = new LinkedList<Service>();
	}

	private String apiName;

	private String introText;

	private final List<Service> services;
}
