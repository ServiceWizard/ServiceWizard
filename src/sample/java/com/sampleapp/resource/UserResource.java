package com.sampleapp.resource;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.sampleapp.model.User;
import com.servicewizard.annotations.Wizard;
import com.servicewizard.annotations.WizardDesc;

@Wizard(name = "Users")
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	public static class Registration {
		@Email
		private String email;

		@NotEmpty
		private String password;

		@NotEmpty
		private String name;

		public Registration() {
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@POST
	@Wizard(
			title = "Register a new account",
			description = "Create a new account with email, password, and name.")
	public User createUser(final @Valid Registration registration) {
		return new User() {
			{
				setId("this-is-mocked");
				setName(registration.getName());
			}
		};
	}

	@GET
	@Wizard(
			title = "List all users",
			description = "A paginated list of all users.")
	public List<User> listUsers(
			@QueryParam("page") @WizardDesc("Page number") @DefaultValue("1") String page,
			@QueryParam("perPage") @WizardDesc("Number of items per page") @DefaultValue("50") String perPage) {
		List<User> users = new LinkedList<>();
		for (int i = 0; i < Integer.parseInt(perPage); i++) {
			User user = new User();
			user.setId("user-" + i);
			user.setName("User # " + i);
			users.add(user);
		}
		return users;
	}

}
