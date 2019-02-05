package com.wol.mock.hplink.dao;

import java.util.Set;

import com.wol.mock.hplink.model.Event;

/**
 * Data access object used for dealing with persistent events
 * 
 * @author tcook
 * @see Event
 */
public interface EventDao extends BasicDao<Event> {

	/**
	 * Attempts to locate an event in persistence
	 * @param id
	 * @return Event with matching primary key, null if not found
	 * @see Event
	 */
	public Event find(Long id);
	
	/**
	 * Retrieve all entitys in persistent storage
	 * @return Set of entitys in persistent storage
	 * @see Set
	 * @see T
	 */
	public Set<Event> findAll();
	
	/**
	 * Attempts locate an event in persistent storage using the UID property.
	 * @param uid
	 * @return Event in storage, null if not found.
	 * @see Event
	 */
	public Event findByUID(String uid);
	
}
