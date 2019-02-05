package com.wol.mock.hplink.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wol.mock.hplink.model.Link;

public class ReassignUIDResponse {
	public static class ReassignUIDResponseDetail {
		public static class Links {
			private Link self;
			private Link product;

			public Links() {
			}

			public Link getSelf() {
				return self;
			}

			public void setSelf(Link self) {
				this.self = self;
			}

			public Link getProduct() {
				return product;
			}

			public void setProduct(Link product) {
				this.product = product;
			}
		}
		
		private String description;
		private String markType;
		private int quantity;
		private String status;
		private String format;
		private String createdAt;
		private String updatedAt;
		private Links links;
		
		public ReassignUIDResponseDetail() {}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@JsonProperty(value="mark_type")
		public String getMarkType() {
			return markType;
		}

		public void setMarkType(String markType) {
			this.markType = markType;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		@JsonProperty(value="created_at")
		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		@JsonProperty(value="updated_at")
		public String getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(String updatedAt) {
			this.updatedAt = updatedAt;
		}

		@JsonProperty(value="_links")
		public Links getLinks() {
			return links;
		}

		public void setLinks(Links links) {
			this.links = links;
		}
		
	}
	
	private ReassignUIDResponseDetail uidJob;

	public ReassignUIDResponse() {}

	@JsonProperty(value="uid_jobs")
	public ReassignUIDResponseDetail getUidJob() {
		return uidJob;
	}

	public void setUidJob(ReassignUIDResponseDetail uidJob) {
		this.uidJob = uidJob;
	}
	
}
