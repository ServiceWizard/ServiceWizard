
package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;

/**
 * A service method (or endpoint) exposed by a web service.
 */
public class ServiceMethod {

	/**
	 * The name of the method as it will appear in generated code.
	 *
	 * This should be in camelCase with no spaces.
	 */
	public String getName() {
		return name;
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
	public HttpVerb getVerb() {
		return verb;
	}

	/**
	 * Sets the HTTP verb for this method
	 */
	public void setVerb(HttpVerb verb) {
		this.verb = verb;
	}

	/**
	 * The URL for this method, relative to the host it is served on
	 */
	public String getRelativePath() {
		return relativePath;
	}

	/**
	 * Sets the URL for this method, relative to the host it is served on
	 */
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	/**
	 * The HTTP query parameters accepted by this method
	 */
	public List<String> getQueryParameters() {
		return queryParameters;
	}

	/**
	 * Add an HTTP parameter this service method will accept
	 */
	public void addQueryParameter(String param) {
		queryParameters.add(param);
	}

	/**
	 * Whether the service method requires a request body.
	 *
	 * POST methods will typically have a request body, while GET methods typically will not.
	 */
	public boolean hasRequestBody() {
		return hasRequestBody;
	}

	/**
	 * Sets whether the service method requires a request body
	 */
	public void setHasRequestBody(boolean hasRequestBody) {
		this.hasRequestBody = hasRequestBody;
	}

	/**
	 * Creates a ServiceMethod with the given name
	 * @param name the name of the service method
	 */
	public ServiceMethod(String name) {
		this.name = name;
		queryParameters = new LinkedList<>();
	}

	private String name;
	private String title;
	private String description;
	private HttpVerb verb;
	private String relativePath;
	private List<String> queryParameters;
	private boolean hasRequestBody;
}
