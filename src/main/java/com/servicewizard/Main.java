
package com.servicewizard;

import com.servicewizard.generation.AngularServiceGenerator;
import com.servicewizard.generation.HtmlDocGenerator;
import com.servicewizard.generation.MarkdownGenerator;
import com.servicewizard.model.Service;

import java.util.List;

public class Main {

	public static void main(String... args) {
		String scanPackage = args[0];
		String outputPath = args[1];
		String moduleName = args[2];
		String urlBase = args[3];

		List<Service> services = new ServiceLocator(scanPackage).locate();

		new MarkdownGenerator().generate(services, outputPath + "/api-documentation.md");
		new HtmlDocGenerator().generate(services, outputPath + "/api-documentation.html");

		for (Service service : services) {
			String serviceOutput = outputPath + "/" + service.getName() + ".js";
			new AngularServiceGenerator().generate(moduleName, urlBase, service, serviceOutput);
		}
	}
}
