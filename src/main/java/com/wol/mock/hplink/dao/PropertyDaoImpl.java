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

import com.wol.mock.hplink.model.Property;

@Repository
public class PropertyDaoImpl implements PropertyDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(PropertyDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	public PropertyDaoImpl() {}

	@Override
	public Property find(Long id) {
		assert(id != null);
		Query findQuery = entityManager.createQuery("FROM Property WHERE id=:id");
		findQuery.setParameter("id", id);
		Property property = null;
		
		try {
			property = (Property)findQuery.getSingleResult();	
		}
		catch(NoResultException e) {}
		
		return property;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Property> findAll() {
		Query findQuery = entityManager.createQuery("FROM Property");
		return new HashSet<Property>(findQuery.getResultList());
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Set<Property> findPropertiesByUID(String uid) {
		assert(StringUtils.isNotBlank(uid));
		Query findQuery = entityManager.createQuery("FROM Event WHERE uid=:uid");
		findQuery.setParameter("uid", uid);
		return new HashSet<Property>(findQuery.getResultList());
	}

	@Override
	@Transactional
	public Property save(Property property) {
		assert(property != null);
		Property existing = find(property.getId());
		
		if(existing != null) {
			property = entityManager.merge(property);
		}
		else {
			entityManager.persist(property);
		}
		
		entityManager.flush();
		LOGGER.debug("Successfully created " + property);
		return property;
	}

	@Override
	@Transactional
	public Property delete(Property property) {
		assert(property != null);
		entityManager.remove(property);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + property);
		return property;
	}

	@Override
	@Transactional
	public int deleteAll() {		
		Set<Property> propertys = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(propertys)) {
			for(Property property : propertys) {
				entityManager.remove(property);
				count++;
			}
			
			entityManager.flush();
			LOGGER.debug("Successfully deleted all properties");
		}
		
		return count;
	}

}
