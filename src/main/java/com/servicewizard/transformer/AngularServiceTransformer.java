
package com.servicewizard.transformer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

import com.servicewizard.Log;
import com.servicewizard.config.TransformerConfiguration;
import com.servicewizard.config.TransformerType;
import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;
import com.servicewizard.model.ServiceMethodParameter;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.formatting.Indentation;
import com.servicewizard.transformer.formatting.PrettyPrintStream;

public class AngularServiceTransformer implements Transformer {

	/**
	 * The name of the parameter that will be passed as the request body
	 */
	public static final String REQUEST_BODY_PARAM_NAME = "data";

	/**
	 * The name of the params object that will contain the query parameters
	 */
	public static final String QUERY_PARAMS_OBJECT_NAME = "params";

	@Override
	public void transformToFile(String urlBase, ServiceModel serviceModel) throws IOException {
		File root = new File(config.getOutputFilePath());
		root.mkdirs();

		createModuleRoot(config.getModuleName(), serviceModel,
				new PrintStream(new File(root, config.getModuleName() + ".js")));

		for (Service service : serviceModel.getServices()) {
			transform(config.getModuleName(), urlBase, service,
					new PrintStream(new File(root, config.getModuleName() + "." + service.getName() + ".js")));
		}
	}

	private void createModuleRoot(String moduleName, ServiceModel serviceModel, PrintStream outputStream)
			throws IOException {
		try (PrettyPrintStream output = new PrettyPrintStream(outputStream)) {
			output.println(String.format("angular.module('%s', []);", moduleName));
		}
	}

	private void transform(String moduleName, String urlBase, Service service, PrintStream outputStream) throws IOException {
		PrettyPrintStream output = new PrettyPrintStream(outputStream);

		addModuleDocumentation(output);

		output.println(String.format("angular.module('%s')", moduleName));
		output.println(String.format(".factory('%s', ['$http', function($http) {", service.getName()));

		try (Indentation bodyIndent = output.indentBlock()) {
			output.println(String.format("var urlBase = '%s';", urlBase));
			output.println("return {");

			try (Indentation returnIndent = output.indentBlock()) {
				for (ServiceMethod method : service.getMethods()) {
					validateMethod(method);
					output.println();

					// Documentation block
					addDocumentationBlock(method, output);

					LinkedList<String> methodParameters = new LinkedList<>();
					boolean hasQueryParameters = !method.getQueryParameters().isEmpty();

					// Path parameters become method parameters
					methodParameters.addAll(method.getPathParameters().stream()
							.map(ServiceMethodParameter::getName)
							.collect(Collectors.toList()));

					// Request body is a parameter called "data"
					if (method.isHasRequestBody())
						methodParameters.add(REQUEST_BODY_PARAM_NAME);

					// Query parameters are optional and become a params object
					if (!method.getQueryParameters().isEmpty())
						methodParameters.add(QUERY_PARAMS_OBJECT_NAME);

					String methodParamsString = methodParameters.stream()
								.collect(Collectors.joining(", "));

					// Function body
					output.println(String.format("%s: function(%s) {", method.getName(), methodParamsString));
					try (Indentation functionIndent = output.indentBlock()) {
						// Request object
						output.println("var request = {");
						try (Indentation requestIndent = output.indentBlock()) {
							output.printListItem(String.format("url: urlBase + %s", templateURL(method.getPath())));
							output.printListItem(String.format("method: '%s'", method.getVerb()));

							if (method.isHasRequestBody())
								output.printListItem(String.format("data: %s", REQUEST_BODY_PARAM_NAME));

							if (hasQueryParameters)
								output.printListItem(String.format("params: %s", QUERY_PARAMS_OBJECT_NAME));
							output.endList();
						}
						output.println("};");
						output.println("return $http(request);");
					}
					output.printListItem("}");
				}
			}
			output.endList();
			output.println("};");
		}
		output.println("}]);");
	}

	private void addDocumentationBlock(ServiceMethod method, PrettyPrintStream output) {
		output.println("/**");

		// Title
		if (method.getTitle() != null)
			output.println(String.format(" * %s", method.getTitle()));

		output.println(" *");

		// Description
		if (method.getDescription() != null) {
			output.println(String.format(" * %s", method.getDescription()));
			output.println(" * ");
		}

		// Path parameters, which are required
		for (ServiceMethodParameter param : method.getPathParameters()) {
			if (param.getDescription() != null)
				output.println(String.format(" * @param %s - %s", param.getName(), param.getDescription()));
			else
				output.println(String.format(" * @param %s", param.getName()));
		}

		// Request body (if required)
		if (method.isHasRequestBody()) {
			if (method.getRequestBodyDescription() != null)
				output.println(String.format(" * @param %s - %s", REQUEST_BODY_PARAM_NAME, method.getRequestBodyDescription()));
			else
				output.println(String.format(" * @param %s", REQUEST_BODY_PARAM_NAME));
		}

		// Params object (if present)
		if (!method.getQueryParameters().isEmpty()) {
			output.println(String.format(" * @param %s - optional parameters", QUERY_PARAMS_OBJECT_NAME));
			for (ServiceMethodParameter parameter : method.getQueryParameters()) {
				// Doc for parameter's description, or empty String
				String description;
				if (parameter.getDescription() != null)
					description = String.format(" - %s", parameter.getDescription());
				else
					description = "";

				// The parameter's name, coupled with default value (if present)
				String name;
				if (parameter.getDefaultValue() != null)
					name = String.format("%s=%s", parameter.getName(), parameter.getDefaultValue());
				else
					name = String.format("%s", parameter.getName());

				output.println(String.format(" * @param [%s.%s]%s", QUERY_PARAMS_OBJECT_NAME, name, description));
			}
		}

		output.println(" */");
	}

	private void addModuleDocumentation(PrettyPrintStream output) {
		output.println("/**");
		output.println(" *");
		String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		output.println(String.format(" * autogenerated by ServiceWizard %s", dateString));
		output.println(" */");
	}

	private String templateURL(String url) {
		boolean endsWithVariable = url.endsWith("}");
		if (endsWithVariable) {
			// Trim off the trailing '}'
			url = url.substring(0, url.length() - 1);
		}

		String urlWithVariables = url.replaceAll("\\{", "' + ").replaceAll("\\}", " + '");

		if (endsWithVariable)
			return "'" + urlWithVariables;

		return "'" + urlWithVariables + "'";
	}

	/**
	 * Validates that the state of the method for code generation. If any properties of the method would prevent
	 * proper code generation, prints a warning to the user detailing the problem.
	 *
	 * Specifically this will check that parameter names do not collide.
	 */
	private void validateMethod(ServiceMethod method) {
		// A path param cannot be the same as the generated request body parameter
		if (method.isHasRequestBody() && method.getPathParameters().stream().anyMatch(
				param -> param.getName().equals(REQUEST_BODY_PARAM_NAME))) {
			Log.warning(String.format(
					"When generating \"%s\": \"%s\" is a path parameter, which conflicts with the parameter generated for request body",
					method.getName(),
					REQUEST_BODY_PARAM_NAME));
		}

		// If query params are present, cannot use generated query params name as a path param
		if (!method.getQueryParameters().isEmpty()
				&& method.getPathParameters().stream().anyMatch(param -> param.getName().equals(QUERY_PARAMS_OBJECT_NAME))) {
			Log.warning(String.format(
					"When generating \"%s\": \"%s\" is a path parameter, which conflicts with the parameter generated for query params",
					method.getName(),
					QUERY_PARAMS_OBJECT_NAME));
		}
	}

	@Override
	public void transform(String urlBase, ServiceModel model, Writer writer) {
		throw new UnsupportedOperationException();
	}

	private final TransformerConfiguration config;

	public AngularServiceTransformer(TransformerConfiguration config) {
		// validate that this config will work for this transformer
		if (config.getTransformerType() != TransformerType.ANGULAR)
			throw new IllegalArgumentException("Invalid transformer type: " + config.getTransformerType().name());
		if (config.getModuleName() == null)
			throw new IllegalArgumentException("Module name is required for angular transformation.");
		if (config.getOutputFilePath() == null)
			throw new IllegalArgumentException("Output path is required for angular transformation.");
		this.config = config;
	}
}
