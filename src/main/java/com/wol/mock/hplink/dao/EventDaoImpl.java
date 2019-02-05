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

import com.wol.mock.hplink.model.Event;

@Repository
public class EventDaoImpl implements EventDao {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	public EventDaoImpl() {}

	@Override
	public Event find(Long id) {
		assert(id != null);
		
		Query findQuery = entityManager.createQuery("FROM Event WHERE id=:id");
		findQuery.setParameter("id", id);
		Event event = null;
		
		try {
			event = (Event)findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return event;
	}

	@Override
	public Event findByUID(String uid) {
		assert(StringUtils.isNotBlank(uid));
		Query findQuery = entityManager.createQuery("FROM Event WHERE uid=:uid");
		findQuery.setParameter("uid", uid);
		Event event = null;
		
		try {
			event = (Event)findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return event;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Event> findAll() {
		Query findQuery = entityManager.createQuery("FROM Event");
		return new HashSet<Event>(findQuery.getResultList());
	}

	@Override
	@Transactional
	public Event save(Event event) {
		assert(event != null);
		Event existing = find(event.getId());
		
		if(existing != null) {
			event = entityManager.merge(event);
		}
		else {
			entityManager.persist(event);
		}
		
		entityManager.flush();
		LOGGER.debug("Successfully saved " + event);
		return event;
	}

	@Override
	@Transactional
	public Event delete(Event event) {
		assert(event != null);
		entityManager.remove(event);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + event);
		return event;
	}

	@Override
	@Transactional
	public int deleteAll() {		
		Set<Event> events = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(events)) {
			for(Event event : events) {
				entityManager.remove(event);
				count++;
			}
			
			entityManager.flush();
			LOGGER.debug("Successfully deleted all events");
		}
		
		return count;
	}

}
