/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.servicewizard;

import com.servicewizard.model.Service;

import java.util.List;

public class Main {

	public static void main(String... args) {
		List<Service> services = new ServiceLocator("com.sampleapp").locate();
		new MarkdownGenerator().generate(services);
	}
}
