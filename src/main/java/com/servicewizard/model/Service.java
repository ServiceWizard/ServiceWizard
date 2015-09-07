
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
public class Service implements Comparable<Service> {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The name of the service.
	 *
	 * This name is used in all generated code, so should not contain spaces and is recommended to
	 * match the class name the service is generated from.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getOrdering() {
		return ordering;
	}

	public void setOrdering(double ordering) {
		this.ordering = ordering;
	}

	@Override
	public int compareTo(Service other) {
		return (int) Math.signum(ordering - other.ordering);
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
	public Service() {
		methods = new LinkedList<>();
	}

	private String title;

	private String name;

	private String description;

	private double ordering;

	private List<ServiceMethod> methods;
}
