package com.wol.mock.hplink.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.wol.mock.hplink.model.Product;

@Repository
public class ProductDaoImpl implements ProductDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	public ProductDaoImpl() {}

	@Override
	public Product find(Long id) {
		assert(id != null);
		Query findQuery = entityManager.createQuery("FROM Product WHERE id=:id");
		findQuery.setParameter("id", id);
		Product product = null;
		
		try {
			product = (Product) findQuery.getSingleResult();	
		}
		catch(NoResultException e) {}
		
		return product;
	}

	@Override
	public Product findByName(String name) {
		assert(StringUtils.isNotBlank(name));
		Query findQuery = entityManager.createQuery("FROM Product WHERE name=:name");
		findQuery.setParameter("name", name);
		Product product = null;
		
		try {
			product = (Product) findQuery.getSingleResult();	
		}
		catch(NoResultException e) {}
		
		return product;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Product> findAll() {
		Query findQuery = entityManager.createQuery("FROM Product");
		return new HashSet<Product>(findQuery.getResultList());
	}

	@Override
	@Transactional
	public Product save(Product product) {
		assert(product != null);
		Product existing = find(Long.valueOf(product.getId()));
		
		if(existing == null) {
			entityManager.persist(product);			
		}
		else {
			product = entityManager.merge(product);
		}
		
		entityManager.flush();
		LOGGER.debug("Successfully saved " + product);
		return product;
	}

	@Override
	@Transactional
	public Product delete(Product product) {
		assert(product != null);
		entityManager.remove(product);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + product);
		return product;
	}

	@Override
	@Transactional
	public int deleteAll() {
		Set<Product> products = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(products)) {
			for(Product product : products) {
				entityManager.remove(product);
				count++;
			}
			
			entityManager.flush();
			LOGGER.debug("Successfully deleted all products");
		}
		
		return count;
	}

}
