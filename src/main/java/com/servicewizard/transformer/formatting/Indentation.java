
package com.servicewizard.transformer.formatting;

public class Indentation implements AutoCloseable {

	public void close() throws Exception {
		indentingStream.unindent();
	}

	public Indentation(PrettyPrintStream indentingStream) {
		this.indentingStream = indentingStream;
		indentingStream.indent();
	}

	private PrettyPrintStream indentingStream;
}
