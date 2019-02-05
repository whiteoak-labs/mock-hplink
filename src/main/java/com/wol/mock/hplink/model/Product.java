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
@Table(name="MHPLINK_PRODUCT")
public class Product {
	
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	@Id
	@Column(nullable=false, unique=true, updatable=false)
	@JsonIgnore
	private long id;
	
	@Column(nullable=false)
	@NotNull
	@JsonProperty
	private String name;
	
	@Column
	@JsonProperty
	private String description;
	
	@Column
	@JsonProperty(value="brand_name")
	private String brandName;
	
	@Column
	@JsonProperty(value="product_url", defaultValue="http://www.wol.com/")
	private String productUrl;
	
	@Column
	@JsonProperty(value="report_fraud_url", defaultValue="http://stopcounterfeitbooks.com")
	private String reportFraudUrl;
	
	@Column
	@JsonProperty(value="show_validation", defaultValue="false")
	private boolean showValidation = false;

	public Product() {
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	@JsonProperty(value="show_validation", defaultValue="false")
	public boolean isShowValidation() {
		return showValidation;
	}

	public void setShowValidation(boolean show_validation) {
		this.showValidation = show_validation;
	}
	
	public String toString() {
		return new StringBuilder("Product[")
				.append("name=")
				.append(name)
				.append("]")
				.toString();
	}
}