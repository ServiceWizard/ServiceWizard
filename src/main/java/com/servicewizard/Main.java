
package com.servicewizard;


import java.io.File;
import java.io.IOException;
import java.util.List;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.Service;
import com.servicewizard.transformer.AngularServiceTransformer;
import com.servicewizard.transformer.MarkdownTransformer;
import com.servicewizard.transformer.Transformer;

public class Main {

	public static void main(String... args) throws IOException {
		String scanPackage = args[0];
		File outputRoot = new File(args[1]);
		String moduleName = args[2];
		String urlBase = args[3];

		if (!outputRoot.exists()) {
			outputRoot.mkdirs();
		}
		List<Service> services = new JerseyResourceLocator(scanPackage).locate();
		
		for (Transformer transformer : new Transformer[] {
				new MarkdownTransformer(),
				new AngularServiceTransformer()
			}) {
			transformer.transform(moduleName, urlBase, services, outputRoot);
		}
	}
}
