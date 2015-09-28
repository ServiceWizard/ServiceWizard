package com.servicewizard.transformer;

import java.io.IOException;
import java.io.Writer;

import com.servicewizard.model.ServiceModel;

public interface Transformer {
	/**
	 * Output code or documentation using this transformer to one or more files, according to its
	 * implementation.
	 * 
	 * @param urlBase The base URL at which the described API will be running.
	 * @param serviceModel
	 * @throws IOException If an exception occurs writing the output files.
	 */
	public void transformToFile(String urlBase, ServiceModel serviceModel) throws IOException;

	/**
	 * Output code or documentation using this transformer to the passed writer. Implementations may
	 * throw <code>NotSupportedException</code> if single-target output is not applicable.
	 * 
	 * @param urlBase The base URL at which the described API will be running.
	 * @param serviceModel
	 * @param writer
	 */
	public void transform(String urlBase, ServiceModel serviceModel, Writer writer) throws IOException;
}
