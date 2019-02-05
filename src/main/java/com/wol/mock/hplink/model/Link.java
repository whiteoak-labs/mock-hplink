package com.wol.mock.hplink.model;

import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Embeddable
public class Link {
	
	@JsonProperty
	private String href;
	
	public Link() {}
	
	public Link(String href) {
		this.href = href;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(this);
		} 
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return new StringBuilder().append("Link [")
				.append("href=").append(href).append("]").toString();
			
	}
}