
package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;

public class Service {

	public String getName() {
		return name;
	}

	public List<ServiceMethod> getMethods() {
		return methods;
	}

	public void addMethod(ServiceMethod method) {
		methods.add(method);
	}

	public Service(String name) {
		this.name = name;
		methods = new LinkedList<>();
	}

	private String name;
	private List<ServiceMethod> methods;
}
