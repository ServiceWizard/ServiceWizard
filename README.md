# ServiceWizard
Automatically generate API documentation and Javascript client code for your Dropwizard project.

## Example output
Take for example a very simple ToDo application's API:

```Java
@Path("/todoItem")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@ServiceWizardService(name="TodoService")
public class ToDoItemService {

	@POST
	@Path("/create")
	@ServiceWizardMethod(
		title="Create todo",
		description="Creates a todo item."
	)
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(ToDoItem item) {
		// ...implementation...
	}

	@GET
	@Path("/get")
	@ServiceWizardMethod(
		title="Get todo item",
		description="Gets an existing todo item by id."
	)
	public ToDoItem get(@QueryParam("id") long id) {
		// ...implementation...
	}

}
```
	
ServiceWizard can create an Angular service to consume this API:
	
```Javascript
angular.module('ToDoApp')
.factory('TodoService', ['$http', function($http) {
    var urlBase = 'http://localhost:8080';
    return {

        /**
         * Create todo
         *
         * Creates a todo item.
        */
        create: function(data) {
            var request = {
                url: urlBase + '/todoItem/create',
                method: 'POST',
                data: data,
            };
            return $http(request);
        },

        /**
         * Get todo item
         *
         * Gets an existing todo item by id.
         * Params:
         *   id
        */
        get: function(params) {
            var request = {
                url: urlBase + '/todoItem/get',
                method: 'GET',
                params: params,
            };
            return $http(request);
        },
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
