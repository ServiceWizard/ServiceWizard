package com.servicewizard;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	public static synchronized void setLogger(Logger l) {
		logger = l;
	}

	public static void setUseLogger(boolean use) {
		useLogger = use;
	}

	public static void info(String message) {
		if (useLogger)
			logger.log(Level.INFO, message);
		else
			System.out.println(message);
	}

	public static void warning(String message) {
		if (useLogger)
			logger.log(Level.WARNING, message);
		else
			System.err.println("WARNING: " + message);
	}

	public static void error(String message) {
		if (useLogger)
			logger.log(Level.SEVERE, message);
		else
			System.err.println("ERROR: " + message);
	}

	public static void error(Throwable t) {
		if (useLogger)
			logger.log(Level.SEVERE, "Exception", t);
		else
			t.printStackTrace(System.err);
	}

	private static final String LOGGER_NAME = "ServiceWizard";

	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	private static boolean useLogger = true;

	private Log() {
	}
}
