package com.wol.mock.hplink.dao;

import java.util.Set;

import com.wol.mock.hplink.model.Property;

public interface PropertyDao extends BasicDao<Property> {

	public Set<Property> findPropertiesByUID(String uid);
	
}
