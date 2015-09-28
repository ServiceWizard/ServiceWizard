package com.sampleapp;

import javax.validation.Valid;

import com.servicewizard.config.ServiceWizardConfiguration;

import io.dropwizard.Configuration;

public class ToDoConfiguration extends Configuration {
	public ServiceWizardConfiguration getServiceWizard() {
		return serviceWizard;
	}

	public void setServiceWizard(ServiceWizardConfiguration serviceWizard) {
		this.serviceWizard = serviceWizard;
	}

	@Valid
	private ServiceWizardConfiguration serviceWizard;
}
