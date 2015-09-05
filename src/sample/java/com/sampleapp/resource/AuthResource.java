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
import com.servicewizard.ServiceWizardMethod;
import com.servicewizard.ServiceWizardService;

@ServiceWizardService(name = "Authentication")
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
	@POST
	@ServiceWizardMethod(
			title = "Sign In")
	public User signin(@Valid Credentials credentials) {
		return new User() {{
			setId("abc-123-this-is-my-user-id");
			setName("Bobby Tables");
			}
		};
	}

	@DELETE
	@ServiceWizardMethod(
			title = "Sign Out")
	public Object signOut() {
		return null;
	}
}
