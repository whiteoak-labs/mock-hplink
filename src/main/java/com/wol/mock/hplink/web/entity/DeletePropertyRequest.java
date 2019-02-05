package com.wol.mock.hplink.web.entity;

import java.util.ArrayList;
import java.util.List;

import com.wol.mock.hplink.model.Property;

public class DeletePropertyRequest {

	private List<Property> properties;
	
	public DeletePropertyRequest() {
		this.properties = new ArrayList<Property>();
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
}
