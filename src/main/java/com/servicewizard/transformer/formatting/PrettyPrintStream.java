
package com.servicewizard.transformer.formatting;

import java.io.PrintStream;

/**
 * Decorates a PrintStream for neatly formatted output.
 *
 * When printing output to the stream, indentation can be controlled using a try-with-resources
 * block to indent and unindent. This makes the indentation level of the code mimic the indentation
 * level of the output.
 *
 * Additionally, this stream internalizes logic for printing comma separated lists and avoiding a
 * trailing comma at the end.
 */
public class PrettyPrintStream implements AutoCloseable {

	/**
	 * Increases the indentation level of all further output
	 */
	protected void indent() {
		++indentation;
		whitespace += "    ";
	}

	/**
	 * Decreases the indentation level of all further output
	 */
	protected void unindent() {
		--indentation;
		whitespace = "";
		for (int i = 0; i<indentation; ++i)
			whitespace += "    ";
	}

	/**
	 * Increases the indentation level and returns an Indentation object to restore the original
	 * state.
	 *
	 * The Indentation is Autocloseable and so can be used in a try-with-resources block.
	 */
	public Indentation indentBlock() {
		return new Indentation(this);
	}

	/**
	 * Prints a value to the PrintStream, indented at the current indentation level
	 */
	public void print(Object object) {
		stream.print(whitespace + object);
	}

	/**
	 * Prints a new line to the print stream.
	 *
	 * If printing in a comma separated list, will print a comma to the end of the previous line.
	 */
	public void println() {
		if (hasTrailingListItem) {
			stream.println(",");
			hasTrailingListItem = false;
		}
		stream.println();
	}

	/**
	 * Prints a value to the PrintStream, indented at the current level and followed by a new line.
	 *
	 * If printing in a comma separated list, will print a comma to the end of the previous line.
	 */
	public void println(Object object) {
		if (hasTrailingListItem) {
			stream.println(",");
			hasTrailingListItem = false;
		}
		stream.println(whitespace + object);
	}

	/**
	 * Prints a value to the PrintStream, indented at the current level and followed by a new line.
	 *
	 * Puts the stream in "list mode", so that commas can be placed at the end of appropriate lines.
	 */
	public void printListItem(Object object) {
		if (hasTrailingListItem)
			stream.println(",");
		print(object);
		hasTrailingListItem = true;
	}

	/**
	 * Takes the stream out of "list mode" so that commas will not be placed at the end of lines.
	 */
	public void endList() {
		hasTrailingListItem = false;
		stream.println();
	}

	/**
	 * Closes the stream
	 */
	public void close() {
		// TODO check for a trailing list item here?
		stream.close();
	}

	/**
	 * Creates a PrettyPrintStream decorating the given stream
	 */
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
