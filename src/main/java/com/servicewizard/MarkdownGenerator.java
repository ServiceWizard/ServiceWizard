/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.servicewizard;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class MarkdownGenerator {

	public void generate(List<Service> services) {
		try {
			generate(services, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void generate(List<Service> services, String fileName) {
		try {
			PrintStream fileWriter = new PrintStream(fileName);
			generate(services, fileWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void generate(List<Service> services, PrintStream output) throws IOException {
		for (Service service : services) {
			output.println("# " + service.getName());

			for (ServiceMethod method : service.getMethods()) {
				String verbName = "[" + method.getVerb() + "]";

				// Join the query parameters
				String paramString = "";
				List<String> parameters = method.getQueryParameters();
				if (!parameters.isEmpty()) {
					paramString += "(" + parameters.get(0);
					for (int i = 1; i<parameters.size(); ++i)
						paramString += ", " + parameters.get(i);
					paramString += ")";
				}

				output.println(String.format("## %-8s %s%s", verbName, method.getRelativePath(), paramString));
			}
			output.println();
		}
	}
}
