package com.wol.mock.hplink.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="MHPL_JOB")
public class Job {

	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	@Column(nullable=false, unique=true, updatable=false)
	@JsonIgnore
	private long id;
	
	@Column
	@JsonProperty
	private String description;
	
	@Column
	@JsonProperty
	private int quantity;
	
	@Column
	@JsonProperty(value="mark_type")
	private String markType;
	
	@Column
	@JsonProperty(value="created_at")
	private Date createdAt;
	
	@Column
	@JsonProperty(value="updated_at")
	private Date updatedAt;
	
	@JsonProperty(value="_links")	
	@Transient
	private Links links;
	
	public Job() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getMarkType() {
		return markType;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Links getLinks() {
		return links;
	}

	public void setLinks(Links links) {
		this.links = links;
	}
	
	public String toString() {		
		return new StringBuilder().append("Job [")
				.append("description=")
				.append(description)
				.append(", markType=")
				.append(markType)
				.append(", quantity=")
				.append(quantity)
				.append(", createdAt=")
				.append(" updateAt=").toString();
	}
}
