package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Property;

public interface PropertyService {

	public Property find(Long id);
	
	public Set<Property> findAll();
	
	public Set<Property> findByUID(String uid);
	
	public Property delete(Property property);
	
	public int deleteAll();
	
	public Property save(Property property);
}
