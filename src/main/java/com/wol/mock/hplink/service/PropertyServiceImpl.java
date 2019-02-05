package com.wol.mock.hplink.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.mock.hplink.dao.PropertyDao;
import com.wol.mock.hplink.model.Property;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	private PropertyDao propertyDao;
	
	public PropertyServiceImpl() {}
	
	public Property find(Long id) {
		return propertyDao.find(id);
	}
	
	public Set<Property> findAll() {
		return (Set<Property>) propertyDao.findAll();
	}

	public Set<Property> findByUID(String uid) {
		return propertyDao.findPropertiesByUID(uid);
	}
	
	public Property delete(Property property) {
		return propertyDao.delete(property);
	}
	
	public int deleteAll() {
		return propertyDao.deleteAll();
	}
	
	public Property save(Property property) {
		return propertyDao.save(property);
	}

}
