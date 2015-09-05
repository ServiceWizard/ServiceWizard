
package com.servicewizard;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.Service;
import com.servicewizard.transformer.AngularServiceTransformer;
import com.servicewizard.transformer.MarkdownTransformer;

import java.util.List;

public class Main {

	public static void main(String... args) {
		String scanPackage = args[0];
		String outputPath = args[1];
		String moduleName = args[2];
		String urlBase = args[3];

		List<Service> services = new JerseyResourceLocator(scanPackage).locate();

		new MarkdownTransformer().transform(services, outputPath + "/api-documentation.md");

		for (Service service : services) {
			String serviceOutput = outputPath + "/" + service.getName() + ".js";
			new AngularServiceTransformer().transform(moduleName, urlBase, service, serviceOutput);
		}
	}
}
