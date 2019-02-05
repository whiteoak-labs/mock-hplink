package com.wol.mock.hplink.web.entity;

import com.wol.mock.hplink.model.Product;

/**
 * Represents an HPLink product request
 *
 */
public class CreateProductRequest {

	private Product product;

	public CreateProductRequest() {
		this.product = new Product();
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
