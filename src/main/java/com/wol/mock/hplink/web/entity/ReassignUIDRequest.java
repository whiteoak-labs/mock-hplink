package com.wol.mock.hplink.web.entity;

import java.util.ArrayList;
import java.util.List;

public class ReassignUIDRequest {

	private List<String> uids;

	public ReassignUIDRequest() {
		this.uids = new ArrayList<String>();
	}

	public List<String> getUids() {
		return uids;
	}

	public void setUids(List<String> uids) {
		this.uids = uids;
	}
	
}
