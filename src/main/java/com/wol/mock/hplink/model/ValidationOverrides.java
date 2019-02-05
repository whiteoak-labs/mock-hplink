package com.wol.mock.hplink.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ValidationOverrides {

	@JsonProperty
	private String uid;
	
	@JsonProperty
	private String reason;
	
	@JsonProperty
	private boolean forceInvalid;
	
	public ValidationOverrides() {}

	public ValidationOverrides(String uid, String reason, boolean forceInvalid) {
		super();
		this.uid = uid;
		this.reason = reason;
		this.forceInvalid = forceInvalid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@JsonProperty(value="force_invalid")
	public boolean isForceInvalid() {
		return forceInvalid;
	}

	public void setForceInvalid(boolean forceInvalid) {
		this.forceInvalid = forceInvalid;
	}
	
	public String toString() {
		return new StringBuilder().append("ValidationOverrides[").append("uid=")
				.append(uid).append(", reason=").append(reason)
				.append(", forceInvalid=").append(forceInvalid)
				.append("]").toString();
	}
}
