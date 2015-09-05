
package com.servicewizard.generation;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

public class MarkdownGenerator {

	public void generate(List<Service> services) {
		try {
			generate(services, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate(List<Service> services, String fileName) {
		try {
			PrintStream fileWriter = new PrintStream(fileName);
			generate(services, fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate(List<Service> services, PrintStream output) throws IOException {
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
