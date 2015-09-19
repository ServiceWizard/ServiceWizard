package com.servicewizard.config;

import org.hibernate.validator.constraints.NotEmpty;

public class TransformerConfiguration {
	/**
	 * The type of transformer to use.
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TransformerType getEnumType() {
		return TransformerType.valueOf(type.toUpperCase());
	}

	/**
	 * Path to the template file to use, relative to the execution root.
	 * 
	 * Ignored when <code>type</code> is <code>ANGULAR</code>.
	 */
	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * Path to output the result of this transformer to, relative to the execution root.
	 * 
	 * If the chosen transformer outputs to multiple files, this path is used as the directory for
	 * that output.
	 */
	public String getOutputFilePath() {
		return outputFilePath;
	}

	public void setOutputFilePath(String outputFilePath) {
		this.outputFilePath = outputFilePath;
	}

	/**
	 * The name of the code module to generate. Ignored if the chosen transformer does not generate
	 * code.
	 */
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * File path for overview text to be used specifically for this transformer. If set, this
	 * overrides any <code>overview</code> set in a containing ServiceWizardConfiguratoin.
	 */
	// TODO nothing uses this yet
	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	@NotEmpty
	private String type;

	private String templatePath;

	private String outputFilePath;

	private String moduleName;

	private String overview;
}
