
package com.servicewizard.generation.formatting;

import java.io.PrintStream;

public class PrettyPrintStream implements AutoCloseable {

	protected void indent() {
		++indentation;
		whitespace += "    ";
	}

	protected void unindent() {
		--indentation;
		whitespace = "";
		for (int i = 0; i<indentation; ++i)
			whitespace += "    ";
	}

	public Indentation indentBlock() {
		return new Indentation(this);
	}

	public void print(Object object) {
		stream.print(whitespace + object);
	}

	public void println() {
		if (hasTrailingListItem) {
			stream.println(",");
			hasTrailingListItem = false;
		}
		stream.println();
	}

	public void println(Object object) {
		if (hasTrailingListItem) {
			stream.println(",");
			hasTrailingListItem = false;
		}
		stream.println(whitespace + object);
	}

	public void printListItem(Object object) {
		if (hasTrailingListItem)
			stream.println(",");
		print(object);
		hasTrailingListItem = true;
	}

	public void endList() {
		hasTrailingListItem = false;
		stream.println();
	}

	public void close() {
		// TODO check for a trailing list item here?
		stream.close();
	}

	public PrettyPrintStream(PrintStream stream) {
		this.stream = stream;

		indentation = 0;
		whitespace = "";
	}

	private int indentation;
	private String whitespace;
	private boolean hasTrailingListItem;

	private PrintStream stream;
}
