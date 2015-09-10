package com.servicewizard.model;

public class ServiceMethodParameter implements Comparable<ServiceMethodParameter> {

	public ServiceMethodParameter(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public int compareTo(ServiceMethodParameter other) {
		return name.compareTo(other.name);
	}

	private final String name;
	private String description;
	private String defaultValue;

}
