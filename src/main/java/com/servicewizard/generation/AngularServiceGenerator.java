
package com.servicewizard.generation;

import com.servicewizard.generation.formatting.Indentation;
import com.servicewizard.generation.formatting.PrettyPrintStream;
import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AngularServiceGenerator {

	public void generate(String moduleName, String urlBase, Service service) {
		generate(moduleName, urlBase, service, System.out);
	}

	public void generate(String moduleName, String urlBase, Service service, String fileName) {
		try {
			generate(moduleName, urlBase, service, new PrintStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void generate(String moduleName, String urlBase, Service service, PrintStream outputStream) {
		try {
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
						boolean hasRequestBody = method.hasRequestBody();

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
								output.printListItem(String.format("url: urlBase + '%s'", method.getRelativePath()));
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			for (String parameter : method.getQueryParameters())
				output.println(String.format(" *   %s", parameter));
		}

		output.println("*/");
	}
}
