/****************************************************************
 * Copyright (c) 2015 Health Innovation Technologies, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Health Innovation Technologies, Inc. ("Confidential Information").
 ****************************************************************/

package com.sampleapp.service;

import com.sampleapp.model.ToDoItem;
import com.servicewizard.ServiceWizardService;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/todoItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@ServiceWizardService(name="TodoService")
public class ToDoItemService {

	@GET
	@Path("/all")
	public List<ToDoItem> allItems() {
		return new LinkedList<>();
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(ToDoItem item) {
	}

	@GET
	@Path("/get")
	public ToDoItem get(@QueryParam("id") long id) {
		return new ToDoItem();
	}

	@DELETE
	@Path("/delete")
	public void delete(@QueryParam("id") long id) {
	}
}
