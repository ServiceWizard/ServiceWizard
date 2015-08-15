
package com.sampleapp.resource;

import com.sampleapp.model.ToDoItem;
import com.servicewizard.ServiceWizardService;

import java.util.Date;
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
		return items;
	}

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(ToDoItem item) {
		items.add(item);
	}

	@GET
	@Path("/get")
	public ToDoItem get(@QueryParam("id") long id) {
		return items.stream()
			.filter(item -> item.getId() == id)
			.findFirst()
			.get();
	}

	@DELETE
	@Path("/delete")
	public void delete(@QueryParam("id") long id) {
		ToDoItem item = get(id);
		if (item != null)
			items.remove(item);
	}

	public ToDoItemService() {
		items = new LinkedList<>();
		items.add(new ToDoItem(1, "Take out the trash", new Date()));
		items.add(new ToDoItem(2, "Brush my teeth", new Date()));
		items.add(new ToDoItem(3, "Walk the dog", new Date()));
		items.add(new ToDoItem(4, "Do my homework", new Date()));
	}

	private List<ToDoItem> items;
}
