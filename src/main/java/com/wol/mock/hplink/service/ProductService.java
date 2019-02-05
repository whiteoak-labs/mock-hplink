package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Product;

public interface ProductService {

	public Product find(Long id);
	
	public Product findByName(String name);
	
	public Set<Product> findAll();
	
	public Product delete(Product product);
	
	public int deleteAll();
	
	public Product save(Product product);
}
