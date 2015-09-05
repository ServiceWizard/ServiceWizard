
package com.servicewizard.transformer;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

public class MarkdownTransformer implements Transformer {

	@Override
	public void transform(String moduleName, String urlBase, List<Service> services, File outputRoot) throws IOException {
		transform(services, new PrintStream(
				new File(outputRoot, "api-documentation.md")));
	}

	private void transform(List<Service> services, PrintStream output) throws IOException {
		for (Service service : services) {
			output.println("# " + service.getName());

			for (ServiceMethod method : service.getMethods()) {
				String verbName = "[" + method.getVerb() + "]";

				// Join the query parameters
				String paramString = "";
				if (!method.getQueryParameters().isEmpty()) {
					paramString = method.getQueryParameters()
					.stream()
					.collect(Collectors.joining(",", "(", ")"));
				}

				output.println(String.format("## %-8s %s%s", verbName, method.getRelativePath(), paramString));

				if (method.getTitle() != null) {
					output.println(method.getTitle());
					output.println();
				}

				if (method.getDescription() != null)
					output.println(method.getDescription());
			}

			output.println();
		}
	}
}
