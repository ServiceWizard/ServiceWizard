package com.servicewizard.transformer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

import com.servicewizard.config.TransformerConfiguration;
import com.servicewizard.config.TransformerType;
import com.servicewizard.model.ServiceModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateTransformer implements Transformer {

	private static final String BUILTIN_HTML_TEMPLATE = "/templates/default-html.ftl";

	private static final String BUILTIN_MARKDOWN_TEMPLATE = "/templates/default-markdown.ftl";

	public static TemplateTransformer getDefaultHTMLTransformer() {
		return new TemplateTransformer(new TransformerConfiguration() {
			{
				setType(TransformerType.DEFAULT_HTML.name());
				setOutputFilePath("documentation.html");
			}
		});
	}

	public static TemplateTransformer getDefaultMarkdownTransformer() {
		return new TemplateTransformer(new TransformerConfiguration() {
			{
				setType(TransformerType.DEFAULT_MARKDOWN.name());
				setOutputFilePath("documentation.markdown");
			}
		});
	}

	@Override
	public void transformToFile(String baseUrl, ServiceModel serviceModel) throws IOException {
		if (config.getOutputFilePath() == null) {
			throw new IllegalStateException(
					"An output file path must be set in order to write to a file.");
		}
		File output = new File(config.getOutputFilePath());
		output.getParentFile().mkdirs();
		transform(baseUrl, serviceModel, new FileWriter(output));
	}

	@Override
	public void transform(String baseUrl, ServiceModel serviceModel, Writer writer) throws IOException {
		try {
			template.process(serviceModel, writer);
		} catch (TemplateException e) {
			// wrap and throw - don't expose template interaction beyond this
			throw new IOException(e);
		}
	}

	public TemplateTransformer(String filePath) {
		this(new TransformerConfiguration() {
			{
				setType(TransformerType.CUSTOM_TEMPLATE.name());
				setTemplatePath(filePath);
			}
		});
	}

	public TemplateTransformer(TransformerConfiguration config){
		Reader reader = null;
		TransformerType type = config.getEnumType();
		if (type == TransformerType.DEFAULT_HTML)
			reader = new InputStreamReader(TemplateTransformer.class.getResourceAsStream(BUILTIN_HTML_TEMPLATE));
		else if (type == TransformerType.DEFAULT_MARKDOWN)
			reader = new InputStreamReader(TemplateTransformer.class.getResourceAsStream(BUILTIN_MARKDOWN_TEMPLATE));
		else if (type == TransformerType.CUSTOM_TEMPLATE) {
			if (config.getTemplatePath() == null)
				throw new IllegalArgumentException("Template path must be set when transformer type is CUSTOM.");
			try {
				reader = new FileReader(new File(config.getTemplatePath()));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else
			throw new IllegalArgumentException("Invalid transformer type for TemplateTransformer: " + type);
		this.config = config;
		Configuration freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
		try {
			template = new Template("html", reader, freemarkerConfig);
		} catch (IOException e) {
			// Don't let this IOException be required up the chain - the application shouldn't
			// start up if it wants to use this and its template is broken.
			throw new RuntimeException("Error initializing template for HTML", e);
		}
	}

	private final Template template;

	private final TransformerConfiguration config;
}
