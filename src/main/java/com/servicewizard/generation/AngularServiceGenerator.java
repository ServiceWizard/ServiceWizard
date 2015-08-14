/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.servicewizard.generation;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AngularServiceGenerator {

	public void generate(String moduleName, Service service) {
		generate(moduleName, service, System.out);
	}

	public void generate(String moduleName, Service service, String fileName) {
		try {
			generate(moduleName, service, new PrintStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void generate(String moduleName, Service service, PrintStream output) {
		indentation = 0;
		whitespace = "";

		output.println(String.format("%sangular.module('%s')", whitespace, moduleName));
		output.println(String.format("%s.factory('%s', ['$http', function($http) {", whitespace, service.getName()));

		indent();
		output.println(String.format("%sreturn {", whitespace));

		indent();
		for (ServiceMethod method : service.getMethods()) {
			output.println();
			if (!method.getQueryParameters().isEmpty())
				output.println(String.format("%s%s: function(params) {", whitespace, method.getName()));
			else
				output.println(String.format("%s%s: function() {", whitespace, method.getName()));

			// Request object
			indent();
			output.println(String.format("%svar request = {", whitespace));

			indent();
			output.println(String.format("%surl: %s,", whitespace, method.getRelativePath()));
			output.println(String.format("%smethod: '%s',", whitespace, method.getVerb()));

			if (!method.getQueryParameters().isEmpty())
				output.println(String.format("%sparams: params,", whitespace));

			unindent();
			output.println(String.format("%s};", whitespace));
			output.println(String.format("%sreturn $http(request);", whitespace));

			unindent();
			output.println(String.format("%s},", whitespace));
		}
		unindent();
		output.println(String.format("%s}", whitespace));
		unindent();
		output.println(String.format("%s}]);", whitespace));
	}

	private void indent() {
		++indentation;
		whitespace += "    ";
	}

	private void unindent() {
		--indentation;
		whitespace = "";
		for (int i = 0; i<indentation; ++i)
			whitespace += "    ";
	}

	private int indentation;
	private String whitespace;
}
