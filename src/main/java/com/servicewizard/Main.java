
package com.servicewizard;

import com.servicewizard.generator.AngularServiceGenerator;
import com.servicewizard.generator.MarkdownGenerator;
import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.Service;

import java.util.List;

public class Main {

	public static void main(String... args) {
		String scanPackage = args[0];
		String outputPath = args[1];
		String moduleName = args[2];
		String urlBase = args[3];

		List<Service> services = new JerseyResourceLocator(scanPackage).locate();

		new MarkdownGenerator().generate(services, outputPath + "/api-documentation.md");

		for (Service service : services) {
			String serviceOutput = outputPath + "/" + service.getName() + ".js";
			new AngularServiceGenerator().generate(moduleName, urlBase, service, serviceOutput);
		}
	}
}
