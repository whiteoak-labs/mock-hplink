package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Event;

public interface EventService {

	public Event find(Long id);
	
	public Set<Event> findAll();
	
	public Event findByUID(String uid);
	
	public Event delete(Event event);
	
	public int deleteAll();
	
	public Event save(Event event);
}
