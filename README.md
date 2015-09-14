# ServiceWizard
Automatically generate API documentation and Javascript client code for your Dropwizard project.

## Example output
Take for example a very simple ToDo application's API:

```Java
@Wizard(name = "ToDos")
@Path("/toDoItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public class ToDoItemResource {

	@POST
	@Wizard(
			title = "Create todo",
			description = "Creates a todo item. An ID will be auto-generated for it.")
	@Consumes(MediaType.APPLICATION_JSON)
	public ToDoItem create(@Valid @WizardDesc("The ToDoItem to be created") ToDoItem item) {
		// ...snip...
	}

	@GET
	@Path("/{id}")
	@Wizard(title = "Retrieve ToDo item")
	public ToDoItem retrieve(@PathParam("id") @WizardDesc("The ID of the item to retrieve") long id) {
		// ...snip...
	}
}
```

Aside from the typical annotations for a Dropwizard project, this class and its methods use the Wizard annotations to
 add documentation information.

By scanning these annotations ServiceWizard can create an Angular service to consume this API:
	
```Javascript
angular.module('ToDoSDK')
.factory('ToDos', ['$http', function($http) {
    var urlBase = 'http://localhost:8080';
    return {

        /**
         * Create todo
         *
         * Creates a todo item. An ID will be auto-generated for it.
         *
         * data - The ToDoItem to be created
        */
        create: function(data) {
            var request = {
                url: urlBase + '/toDoItem',
                method: 'POST',
                data: data
            };
            return $http(request);
        },

        /**
         * Retrieve ToDo item
         *
         * id - The ID of the item to retrieve
        */
        retrieve: function(id) {
            var request = {
                url: urlBase + '/toDoItem/' + id,
                method: 'GET'
            };
            return $http(request);
        }
    };
}]);
```

It can also create documentation for this API in Markdown:
# TodoService
## [POST]   /todoItem/create
Create todo

Creates a todo item.
## [GET]    /todoItem/get(id)
Get todo item

Gets an existing todo item by id.

# Building from source

```
$ gradle shadow
```
