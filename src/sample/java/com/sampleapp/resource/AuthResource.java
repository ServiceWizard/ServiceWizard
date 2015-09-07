package com.sampleapp.resource;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sampleapp.model.Credentials;
import com.sampleapp.model.User;
import com.servicewizard.annotations.Wizard;

@Wizard(
		name = "Authentication",
		description = "Sign in and out of accounts.",
		ordering = 1)
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
	@POST
	@Wizard(
			title = "Sign In",
			description = "Sign into a user account with email and password.",
			ordering = 1)
	public User signin(@Valid Credentials credentials) {
		return new User() {{
			setId("abc-123-this-is-my-user-id");
			setName("Bobby Tables");
			}
		};
	}

	@DELETE
	@Wizard(
			title = "Sign Out",
			requiresAuthentication = "true",
			ordering = 2)
	public Object signOut() {
		return null;
	}
}
