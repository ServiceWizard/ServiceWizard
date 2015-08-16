
package com.servicewizard.model;

import java.util.LinkedList;
import java.util.List;

public class ServiceMethod {

	public String getName() {
		return name;
	}

	public HttpVerb getVerb() {
		return verb;
	}

	public void setVerb(HttpVerb verb) {
		this.verb = verb;
	}

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public List<String> getQueryParameters() {
		return queryParameters;
	}

	public void addQueryParameter(String param) {
		queryParameters.add(param);
	}

	public boolean hasRequestBody() {
		return hasRequestBody;
	}

	public void hasRequestBody(boolean hasRequestBody) {
		this.hasRequestBody = hasRequestBody;
	}

	public ServiceMethod(String name) {
		this.name = name;
		queryParameters = new LinkedList<>();
	}

	private String name;
	private HttpVerb verb;
	private String relativePath;
	private List<String> queryParameters;
	private boolean hasRequestBody;
}
