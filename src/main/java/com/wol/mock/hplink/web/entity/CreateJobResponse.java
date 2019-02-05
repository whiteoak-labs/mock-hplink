package com.wol.mock.hplink.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wol.mock.hplink.model.Link;

public class CreateJobResponse {
	public static class Job {
		public static class Links {
			private Link self;
			private Link values;
			private Link jobFile;
			private Link product;
			private Link markJob;
			private Link reassignUIDs;

			public Link getSelf() {
				return self;
			}

			public void setSelf(Link self) {
				this.self = self;
			}

			public Link getValues() {
				return values;
			}

			public void setValues(Link values) {
				this.values = values;
			}

			@JsonProperty(value="job_file")
			public Link getJobFile() {
				return jobFile;
			}

			public void setJobFile(Link jobFile) {
				this.jobFile = jobFile;
			}

			public Link getProduct() {
				return product;
			}

			public void setProduct(Link product) {
				this.product = product;
			}

			@JsonProperty(value="mark_job")
			public Link getMarkJob() {
				return markJob;
			}

			public void setMarkJob(Link markJob) {
				this.markJob = markJob;
			}

			@JsonProperty(value="reassign_uids")
			public Link getReassignUIDs() {
				return reassignUIDs;
			}

			public void setReassignUIDs(Link reassignUIDs) {
				this.reassignUIDs = reassignUIDs;
			}

		}

		private String description;
		private int quantity;
		private String markType;
		private String status;
		private String createdAt;
		private String updatedAt;
		private Links links;

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

		@JsonProperty(value = "mark_type")
		public String getMarkType() {
			return markType;
		}

		public void setMarkType(String mark_type) {
			this.markType = mark_type;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		@JsonProperty(value = "created_at")
		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String created_at) {
			this.createdAt = created_at;
		}

		@JsonProperty(value = "updated_at")
		public String getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(String updated_at) {
			this.updatedAt = updated_at;
		}

		public Links getLinks() {
			return links;
		}

		public void setLinks(Links links) {
			this.links = links;
		}
	}

	private Job uidJob;

	public CreateJobResponse() {
	}

	@JsonProperty(value = "uid_job")
	public Job getUidJob() {
		return uidJob;
	}

	public void setUidJob(Job uidJob) {
		this.uidJob = uidJob;
	}

}