package com.wol.mock.hplink.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.mock.hplink.dao.EventDao;
import com.wol.mock.hplink.model.Event;

@Service
public class EventServiceImpl implements EventService {
	
	@Autowired
	private EventDao eventDao;
	
	public EventServiceImpl() {}
	
	public Event find(Long id) {
		return eventDao.find(id);
	}
	
	public Set<Event> findAll() {
		return (Set<Event>)eventDao.findAll();
	}
	
	public Event findByUID(String uid) {
		return eventDao.findByUID(uid);
	}
	
	public Event delete(Event event) {
		return eventDao.delete(event);
	}
	
	public int deleteAll() {
		return eventDao.deleteAll();
	}
	
	public Event save(Event event) {
		return eventDao.save(event);
	}
	
	
}
