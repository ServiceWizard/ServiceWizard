
package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service method (or endpoint) exposed by a web service.
 */
public class ServiceMethod implements Comparable<ServiceMethod> {

	/**
	 * The name of the method as it will appear in generated code.
	 *
	 * This should be in camelCase with no spaces.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * The title of the method.
	 *
	 * This is a human-friendly title used in documentation and may contain spaces.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of this service method
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The description of the method.
	 *
	 * A simple description of the method's behavior and intended use, as it should appear in
	 * documentation.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the method description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * The verb describing the service method's HTTP request method
	 */
	public String getVerb() {
		return verb;
	}

	/**
	 * The URL for this method, relative to the host it is served on
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the URL for this method, relative to the host it is served on
	 */
	public void setPath(String path) {
		this.path = path;
	}

	public boolean isRequiresAuthentication() {
		return requiresAuthentication;
	}

	public void setRequiresAuthentication(boolean requiresAuthentication) {
		this.requiresAuthentication = requiresAuthentication;
	}

	public String getPermissionRequired() {
		return permissionRequired;
	}

	public void setPermissionRequired(String permissionRequired) {
		this.permissionRequired = permissionRequired;
	}

	/**
	 * The HTTP query parameters accepted by this method
	 */
	public List<ServiceMethodParameter> getQueryParameters() {
		return queryParameters;
	}

	/**
	 * Add an HTTP parameter this service method will accept
	 */
	public void addQueryParameter(ServiceMethodParameter param) {
		queryParameters.add(param);
	}

	public List<String> getQueryParameterNames() {
		return queryParameters.stream()
				.map(p -> p.getName())
				.collect(Collectors.toList());
	}

	/**
	 * The path parameters required by this method
	 */
	public List<ServiceMethodParameter> getPathParameters() {
		return pathParameters;
	}

	/**
	 * Add a path parameter this service method requires.
	 */
	public void addPathParameter(ServiceMethodParameter param) {
		pathParameters.add(param);
	}

	/**
	 * Whether the service method requires a request body.
	 *
	 * POST methods will typically have a request body, while GET methods typically will not.
	 */
	// name sucks - hooray bean patterns!
	public boolean isHasRequestBody() {
		return hasRequestBody;
	}

	/**
	 * Sets whether the service method requires a request body
	 */
	public void setHasRequestBody(boolean hasRequestBody) {
		this.hasRequestBody = hasRequestBody;
	}

	public double getOrdering() {
		return ordering;
	}

	public void setOrdering(double ordering) {
		this.ordering = ordering;
	}

	@Override
	public int compareTo(ServiceMethod other) {
		int sign = (int) Math.signum(ordering - other.ordering);
		return sign == 0 ? title.compareTo(other.title) : sign;
	}

	/**
	 * Creates a ServiceMethod with the given name
	 * @param name the name of the service method
	 */
	public ServiceMethod(String name, String verb) {
		this.name = name;
		this.verb = verb;
		queryParameters = new LinkedList<>();
		pathParameters = new LinkedList<>();
	}

	private String name;
	private String title;
	private String description;
	private final String verb;
	private String path;
	private boolean requiresAuthentication;
	private String permissionRequired;
	private final List<ServiceMethodParameter> queryParameters;
	private final List<ServiceMethodParameter> pathParameters;
	private boolean hasRequestBody;
	private double ordering;
}
