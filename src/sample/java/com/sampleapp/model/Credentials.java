package com.sampleapp.model;

import org.hibernate.validator.constraints.NotEmpty;

public class Credentials {
	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	public Credentials() {
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
}
