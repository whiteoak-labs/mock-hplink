package com.wol.mock.hplink.model;

import java.util.Date;

import com.wol.mock.hplink.Utils;

public class Session {

	private String token;
	private Date created;
	private Date validUntil;
	private Date invalidatedAt;
	private boolean admin = false;
	private boolean valid = true;

	public Session() {}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getValidUntil() {
		return validUntil;
	}

	public void setValidUntil(Date validUntil) {
		this.validUntil = validUntil;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Date getInvalidatedAt() {
		return invalidatedAt;
	}

	public void setInvalidatedAt(Date invalidatedAt) {
		this.invalidatedAt = invalidatedAt;
	}

	public String toString() {
		return new StringBuilder().append("Session[")
				.append("token=")
				.append(token)
				.append(", created=")
				.append(Utils.formatDate(created, null))
				.append(", valid_until=")
				.append(Utils.formatDate(validUntil, null))
				.append("]").toString();
	}
}

