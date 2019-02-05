package com.wol.mock.hplink.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SystemAdminRequest {

	@JsonProperty
	private boolean resetDaatabase = false;
	
	@JsonProperty
	private boolean resetWebServer = false;

	protected SystemAdminRequest() {}

	public boolean isResetDaatabase() {
		return resetDaatabase;
	}

	public void setResetDaatabase(boolean resetDaatabase) {
		this.resetDaatabase = resetDaatabase;
	}

	public boolean isResetWebServer() {
		return resetWebServer;
	}

	public void setResetWebServer(boolean resetWebServer) {
		this.resetWebServer = resetWebServer;
	}
	
	
}
