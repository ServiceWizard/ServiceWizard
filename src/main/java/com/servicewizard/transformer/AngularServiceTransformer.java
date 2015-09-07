
package com.servicewizard.transformer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;
import com.servicewizard.model.ServiceMethodParameter;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.formatting.Indentation;
import com.servicewizard.transformer.formatting.PrettyPrintStream;

public class AngularServiceTransformer implements Transformer {

	@Override
	public void transform(String moduleName, String urlBase, ServiceModel serviceModel, File outputRoot) throws IOException {
		for (Service service : serviceModel.getServices()) {
			
			transform(moduleName, urlBase, service,
					new PrintStream(new File(outputRoot, service.getName() + ".js")));
		}
	}

	private void transform(String moduleName, String urlBase, Service service, PrintStream outputStream) throws IOException {
		PrettyPrintStream output = new PrettyPrintStream(outputStream);

		output.println(String.format("angular.module('%s')", moduleName));
		output.println(String.format(".factory('%s', ['$http', function($http) {", service.getName()));

		try (Indentation bodyIndent = output.indentBlock()) {
			output.println(String.format("var urlBase = '%s';", urlBase));
			output.println("return {");

			try (Indentation returnIndent = output.indentBlock()) {
				for (ServiceMethod method : service.getMethods()) {
					output.println();

					// Documentation block
					addDocumentationBlock(method, output);

					// Method parameters
					boolean hasQueryParameters = !method.getQueryParameters().isEmpty();
					boolean hasRequestBody = method.isHasRequestBody();

					// Function body
					if (hasRequestBody && hasQueryParameters)
						output.println(String.format("%s: function(data, params) {", method.getName()));
					else if (hasRequestBody)
						output.println(String.format("%s: function(data) {", method.getName()));
					else if (hasQueryParameters)
						output.println(String.format("%s: function(params) {", method.getName()));
					else
						output.println(String.format("%s: function() {", method.getName()));
					try (Indentation functionIndent = output.indentBlock()) {
						// Request object
						output.println("var request = {");
						try (Indentation requestIndent = output.indentBlock()) {
							output.printListItem(String.format("url: urlBase + '%s'", method.getPath()));
							output.printListItem(String.format("method: '%s'", method.getVerb()));

							if (hasRequestBody)
								output.printListItem("data: data");

							if (hasQueryParameters)
								output.printListItem("params: params");
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

	public void addDocumentationBlock(ServiceMethod method, PrettyPrintStream output) {
		output.println("/**");

		// Title
		if (method.getTitle() != null)
			output.println(String.format(" * %s", method.getTitle()));

		output.println(" *");

		// Description
		if (method.getDescription() != null)
			output.println(String.format(" * %s", method.getDescription()));

		// Parameters
		if (!method.getQueryParameters().isEmpty()) {
			output.println(" * Params:");
			for (ServiceMethodParameter parameter : method.getQueryParameters())
				output.println(String.format(" *   %s", parameter.getName()));
		}

		output.println("*/");
	}

}
