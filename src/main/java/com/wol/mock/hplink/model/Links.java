package com.wol.mock.hplink.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="MHPL_LINK")
public class Links {

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	@Column(nullable=false, unique=true)
	@JsonIgnore
	private long id;
	
	@JsonProperty(value="self")
	private String self;

	@JsonProperty(value="uid_jobs")
	private String uidJobs;
	
	@JsonProperty(value="parent")
	private String parent;

	public String getSelf() {
		return self;
	}

	public Link getSelfLink() {
		return new Link(self);
	}
	
	public void setSelf(String self) {
		this.self = self;
	}
	
	public String getUidJobs() {
		return uidJobs;
	}

	public Link getUidJobsLink() {
		return new Link(uidJobs);
	}
	
	public void setUidJobs(String uid_jobs) {
		this.uidJobs = uid_jobs;
	}

	public String getParent() {
		return parent;
	}
	
	public Link getParentLink() {
		return new Link(parent);
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	public String toString() {
		return new StringBuilder().append("Link[").append("self=").append(self)
				.append(", uidJobs=").append(uidJobs).append(", parent=")
				.append(parent).append("]").toString();
	}
}