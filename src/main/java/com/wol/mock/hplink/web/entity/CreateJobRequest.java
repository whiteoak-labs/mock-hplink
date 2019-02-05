package com.wol.mock.hplink.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateJobRequest {
	public class UIDJob {
		private String description;
		private int quantity;
		private String markType;

		public UIDJob() {
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

		@JsonProperty(value="mark_type", defaultValue="secure_qr_code")
		public String getMarkType() {
			return markType;
		}

		public void setMarkType(String mark_type) {
			this.markType = mark_type;
		}
	}
	
	private UIDJob uidJob;

	public CreateJobRequest(String description, int quantity) {
		this.uidJob = new UIDJob();
		this.uidJob.setDescription(description);
		this.uidJob.setQuantity(quantity);
	}

	@JsonProperty(value="uid_job")
	public UIDJob getUidJob() {
		return uidJob;
	}

	public void setUidJob(UIDJob uid_job) {
		this.uidJob = uid_job;
	}

}