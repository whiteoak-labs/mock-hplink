package com.wol.mock.hplink.dao;

import com.wol.mock.hplink.model.Product;

public interface ProductDao extends BasicDao<Product> {

	public Product findByName(String name);
	
}
