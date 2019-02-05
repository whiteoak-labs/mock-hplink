package com.wol.mock.hplink.web.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wol.mock.hplink.model.Links;

public class ProductResponse {
	public static class Product {
		
		private String name;
		private String description;
		
		@JsonProperty(value="brand_name")
		private String brandName;
		
		@JsonProperty(value="product_url")
		private String productUrl;
		
		@JsonProperty(value="report_fraud_Url")
		private String reportFraudUrl;
		
		@JsonProperty(value="show_validation", defaultValue="false")
		private boolean showValidation;
		
		@JsonProperty(value="created_at")
		private String createdAt;
		
		@JsonProperty(value="updated_at")
		private String updatedAt;
		
		@JsonProperty(value="_links")
		private Links links;

		public Product() {}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getBrandName() {
			return brandName;
		}

		public void setBrandName(String brand_name) {
			this.brandName = brand_name;
		}

		public String getProductUrl() {
			return productUrl;
		}

		public void setProductUrl(String product_url) {
			this.productUrl = product_url;
		}

		public String getReportFraudUrl() {
			return reportFraudUrl;
		}

		public void setReportFraudUrl(String report_fraud_url) {
			this.reportFraudUrl = report_fraud_url;
		}

		public boolean isShowValidation() {
			return showValidation;
		}

		public void setShowValidation(boolean show_validation) {
			this.showValidation = show_validation;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String created_at) {
			this.createdAt = created_at;
		}

		public String getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(String updated_at) {
			this.updatedAt = updated_at;
		}

		public Links getLinks() {
			return links;
		}

		public void setLinks(Links _links) {
			this.links = _links;
		}
	}
	
	@JsonProperty(value="product")
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}