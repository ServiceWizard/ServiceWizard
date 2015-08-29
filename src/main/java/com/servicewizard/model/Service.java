
package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;

/**
 * Comprises multiple related HTTP endpoints.
 *
 * This is an intermediate representation that code generation acts on, abstracting away the
 * original source annotations.
 *
 * A service corresponds to a single Java class, such as a "resource" or "controller".
 *
 * Each service is generated into a single Javascript file.
 */
public class Service {

	/**
	 * The name of the service.
	 *
	 * This name is used in all generated code, so should not contain spaces and is recommended to
	 * match the class name the service is generated from.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The methods exposed by the service
	 */
	public List<ServiceMethod> getMethods() {
		return methods;
	}

	/**
	 * Add a method to the service
	 */
	public void addMethod(ServiceMethod method) {
		methods.add(method);
	}

	/**
	 * Create a service with the given name, containing no methods.
	 */
	public Service(String name) {
		this.name = name;
		methods = new LinkedList<>();
	}

	private String name;

	private List<ServiceMethod> methods;
}
