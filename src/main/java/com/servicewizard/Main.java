
package com.servicewizard;


import java.io.File;
import java.io.IOException;

import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.AngularServiceTransformer;
import com.servicewizard.transformer.TemplateTransformer;
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
		
		ServiceModel model = new ServiceModel();
		model.setApiName(moduleName); // TODO allow this to be set better
		model.getServices().addAll(new JerseyResourceLocator(scanPackage).locate());

		for (Transformer transformer : new Transformer[] {
				new AngularServiceTransformer(),
				TemplateTransformer.getDefaultMarkdownTransformer(),
				TemplateTransformer.getDefaultHTMLTransformer()
			}) {
			transformer.transform(moduleName, urlBase, model, outputRoot);
		}
	}
}
