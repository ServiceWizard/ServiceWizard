package com.servicewizard.transformer;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.servicewizard.model.Service;

public interface Transformer {
	/**
	 * Transform the service model into a specific format.
	 * 
	 * @param moduleName The name of the module to build (if applicable).
	 * @param urlBase The base URL at which the described API will be running.
	 * @param services The service model.
	 * @param outputRoot The root output directory to build into.
	 * @throws IOException If an exception occurs writing the output files.
	 */
	public void transform(String moduleName, String urlBase, List<Service> services, File outputRoot) throws IOException;
}