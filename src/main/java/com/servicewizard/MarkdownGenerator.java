/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.servicewizard;

import com.servicewizard.model.Service;
import com.servicewizard.model.ServiceMethod;

import java.util.List;

public class MarkdownGenerator {

	public void generate(List<Service> services) {
		for (Service service : services) {
			System.out.println("# " + service.getName());

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

				System.out.println(String.format("## %-8s %s%s", verbName, method.getRelativePath(), paramString));
			}
			System.out.println();
		}
	}
}
