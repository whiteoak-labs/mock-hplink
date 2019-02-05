package com.wol.mock.hplink.web.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wol.mock.hplink.model.ValidationOverrides;

public class ValidationOverridesRequest {

	public List<ValidationOverrides> validationOverrides;
	
	public ValidationOverridesRequest() {
		this.validationOverrides = new ArrayList<ValidationOverrides>();
	}

	@JsonProperty(value="validation_overrides")
	public List<ValidationOverrides> getValidationOverrides() {
		return validationOverrides;
	}

	public void setValidationOverrides(List<ValidationOverrides> validationOverrides) {
		this.validationOverrides = validationOverrides;
	}
	
}
