
package com.sampleapp.resource;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sampleapp.model.ToDoItem;
import com.servicewizard.annotations.Wizard;
import com.servicewizard.annotations.WizardDesc;

@Wizard(name = "ToDos")
@Path("/toDoItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ToDoItemResource {

	@GET
	@Wizard(
			title = "Get all",
			description = "Gets all active todo items.")
	public List<ToDoItem> allItems() {
		return items;
	}

	@POST
	@Wizard(
			title = "Create todo",
			description = "Creates a todo item. An ID will be auto-generated for it.")
	@Consumes(MediaType.APPLICATION_JSON)
	public ToDoItem create(@Valid ToDoItem item) {
		item.setId(nextId++);
		items.add(item);
		return item;
	}

	@GET
	@Path("/{id}")
	@Wizard(title = "Retrieve ToDo item")
	public ToDoItem retrieve(@PathParam("id") @WizardDesc("The ID of the item to retrieve") long id) {
		return items.stream()
				.filter(item -> item.getId() == id)
				.findFirst()
				.get();
	}

	@DELETE
	@Path("/{id}")
	@Wizard(title = "Delete ToDo item")
	public void delete(@PathParam("id") @WizardDesc("The ID of the item to delete") long id) {
		items.remove(items.stream()
				.filter(item -> item.getId() == id)
				.findFirst());
	}

	public ToDoItemResource() {
		items = new LinkedList<>();
		items.add(new ToDoItem(1, "Take out the trash", new Date()));
		items.add(new ToDoItem(2, "Brush my teeth", new Date()));
		items.add(new ToDoItem(3, "Walk the dog", new Date()));
		items.add(new ToDoItem(4, "Do my homework", new Date()));
		nextId = 5;
	}

	private List<ToDoItem> items;
	private int nextId;
}
