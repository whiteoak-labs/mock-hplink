package com.wol.mock.hplink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
@Entity
@Table(name="MHPLINK_EVENT")
public class Event {

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	@Column(nullable=false, unique=true, updatable=false)
	@JsonIgnore
	private long id;
	
	@Column(nullable=false)
	@NotNull
	@JsonProperty
	private String uid;
	
	@Column(nullable=false)
	@NotNull
	@JsonProperty
	private String name;
	
	@Column
	@JsonProperty
	private String value;

	public Event() {
	}
	
	public Event(String uid, String name, String value) {
		super();
		this.uid = uid;
		this.name = name;
		this.value = value;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return new StringBuilder().append("Event [")
				.append("id=").append(id)
				.append(", ")
				.append("uid=")
				.append(uid)
				.append(", ")
				.append("name=")
				.append(name)
				.append(", ")
				.append("value=")
				.append(value)
				.append("]")
				.toString();
	}
}
