package com.wol.mock.hplink.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.mock.hplink.dao.ProductDaoImpl;
import com.wol.mock.hplink.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDaoImpl productDao;
	
	public ProductServiceImpl() {}
	
	public Product find(Long id) {
		return productDao.find(id);
	}
	
	public Product findByName(String name) {
		return productDao.findByName(name);
	}
	
	public Set<Product> findAll() {
		return (Set<Product>) productDao.findAll();
	}
	
	public Product delete(Product product) {
		return productDao.delete(product);
	}
	
	public int deleteAll() {
		return productDao.deleteAll();
	}
	
	public Product save(Product product) {
		return productDao.save(product);
	}
}
