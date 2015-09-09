package com.servicewizard.transformer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;

import com.servicewizard.model.ServiceModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class TemplateTransformer implements Transformer {

	private static final String BUILTIN_HTML_TEMPLATE = "/templates/default-html.ftl";

	private static final String BUILTIN_MARKDOWN_TEMPLATE = "/templates/default-markdown.ftl";

	public static TemplateTransformer getDefaultHTMLTransformer() {
		return new TemplateTransformer(new InputStreamReader(
				TemplateTransformer.class.getResourceAsStream(BUILTIN_HTML_TEMPLATE)), "index.html");
	}

	public static TemplateTransformer getDefaultMarkdownTransformer() {
		return new TemplateTransformer(new InputStreamReader(
				TemplateTransformer.class.getResourceAsStream(BUILTIN_MARKDOWN_TEMPLATE)), "api-documentation.md");
	}

	@Override
	public void transform(String moduleName, String baseUrl, ServiceModel serviceModel, File outputRoot) throws IOException {
		if (targetFile == null) {
			// this is a really miserable situation - we should find a better way to handle the whole file output scenario
			throw new IllegalArgumentException("Attempting to transform to file output without having set a target filename.");
		}
		transform(serviceModel, new FileWriter(new File(outputRoot, targetFile)));
	}

	public void transform(ServiceModel serviceModel, Writer writer) throws IOException {
		try {
			template.process(serviceModel, writer);
		} catch (TemplateException e) {
			// wrap and throw - don't expose template interaction beyond this
			throw new IOException(e);
		}
	}

	public void setTargetFile(String targetFile) {
		this.targetFile = targetFile;
	}

	public TemplateTransformer(String templateContents) {
		this(new StringReader(templateContents), null);
	}

	public TemplateTransformer(File file) throws IOException {
		this(new FileReader(file), null);
	}

	private TemplateTransformer(Reader reader, String targetFile) {
		this.targetFile = targetFile;
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

	private String targetFile;
}
