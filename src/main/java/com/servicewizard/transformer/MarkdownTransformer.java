
package com.servicewizard.transformer;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownTransformer {

	public void transform(List<Service> services) {
		try {
			transform(services, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void transform(List<Service> services, String fileName) {
		try {
			PrintStream fileWriter = new PrintStream(fileName);
			transform(services, fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
