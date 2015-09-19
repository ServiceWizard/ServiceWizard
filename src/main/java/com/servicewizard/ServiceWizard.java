
package com.servicewizard;


import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.common.io.Files;
import com.servicewizard.config.ServiceWizardConfiguration;
import com.servicewizard.config.TransformerConfiguration;
import com.servicewizard.config.TransformerType;
import com.servicewizard.locator.JerseyResourceLocator;
import com.servicewizard.model.ServiceModel;
import com.servicewizard.transformer.AngularServiceTransformer;
import com.servicewizard.transformer.TemplateTransformer;
import com.servicewizard.transformer.Transformer;

public class ServiceWizard {

	public static void main(String... args) throws IOException, ParseException {
		Options options = new Options()
				.addOption(Option
						.builder("t")
						.longOpt("type")
						.hasArg()
						.desc("The template type to use. Choose from: " + TransformerType.getNameString())
						.required()
						.build())
				.addOption(Option
						.builder("template")
						.hasArg()
						.desc("Relative path to a file to be used as a custom template.")
						.build())
				.addOption(Option
						.builder("f")
						.longOpt("output-file")
						.hasArg()
						.desc("Output file path.")
						.build())
				.addOption(Option
						.builder("m")
						.longOpt("module")
						.hasArg()
						.desc("The name of the module to build code for, if applicable.")
						.build())
				.addOption(Option
						.builder("n")
						.longOpt("name")
						.hasArg()
						.desc("The name of the API.")
						.build())
				.addOption(Option
						.builder("u")
						.longOpt("url")
						.hasArg()
						.desc("The base URL at which the API will be running.")
						.required()
						.build())
				.addOption(Option
						.builder("overview")
						.hasArg()
						.desc("Relative path ot a file to be used as overview text.")
						.build());
		CommandLine commands = new DefaultParser().parse(options, args);
		
		if (commands.getArgList().size() < 1)
			throw new IllegalArgumentException("Package path required.");
		String packagePath = commands.getArgList().get(0);

		ServiceWizardConfiguration config = new ServiceWizardConfiguration();
		config.setBaseUrl(commands.getOptionValue("u"));
		config.setApiName(commands.getOptionValue("n"));
		config.setOverviewPath(commands.getOptionValue("overview"));

		TransformerConfiguration transformerConfig = new TransformerConfiguration();
		transformerConfig.setModuleName(commands.getOptionValue("m"));
		transformerConfig.setType(commands.getOptionValue("t"));
		transformerConfig.setTemplatePath(commands.getOptionValue("template"));
		transformerConfig.setOutputFilePath(commands.getOptionValue("f"));
		config.getTransformers().add(transformerConfig);
		
		process(config, packagePath);
	}

	/**
	 * Working from a valid ServiceWizardConfiguration object, builds a model of all services
	 * contained within the application and outputs the transformed result as instructed.
	 * 
	 * @param config
	 * @param packagePath
	 * @throws IOException
	 */
	public static void process(ServiceWizardConfiguration config, String packagePath) throws IOException {
		
		ServiceModel model = new ServiceModel();
		model.setApiName(config.getApiName());
		if (config.getOverviewPath() != null)
			model.setOverview(new String(Files.toByteArray(new File(config.getOverviewPath()))));
		model.getServices().addAll(new JerseyResourceLocator(packagePath).locate());

		for (TransformerConfiguration transformerConfig : config.getTransformers()) {
			Transformer transformer = getTransformer(transformerConfig);
			if (transformer == null)
				throw new RuntimeException("Unable to find transformer for type: " + transformerConfig.getType());
			transformer.transformToFile(config.getBaseUrl(), model);
		}
	}

	private static Transformer getTransformer(TransformerConfiguration config) {
		switch (config.getEnumType()) {
			case ANGULAR:
				return new AngularServiceTransformer(config);
			case CUSTOM:
			case DEFAULT_HTML:
			case DEFAULT_MARKDOWN:
				return new TemplateTransformer(config);
		}
		return null;
	}
}
