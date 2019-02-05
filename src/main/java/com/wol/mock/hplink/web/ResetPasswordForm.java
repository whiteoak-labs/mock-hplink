package com.wol.mock.hplink.web;

import javax.validation.constraints.NotNull;

public class ResetPasswordForm 
{
	@NotNull
	String email;
	
	public ResetPasswordForm() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}