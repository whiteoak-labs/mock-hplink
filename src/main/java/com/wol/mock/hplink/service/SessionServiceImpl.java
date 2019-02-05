package com.wol.mock.hplink.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.wol.mock.hplink.Utils;
import com.wol.mock.hplink.model.Session;

@Service
public class SessionServiceImpl implements SessionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SessionServiceImpl.class);
	
	private volatile List<Session> sessions = new ArrayList<Session>();
	
	@Component
	class SessionManagementTask {
	
		@Scheduled(fixedDelay=SESSION_VALIDATION_TIME_MS)
		public void sweepSessions() {
			if(sessions.isEmpty()) {
				return;
			}
			
			LOGGER.info("Performing session maintenance task: Invalidate Expired Sessions");
			Date now = new Date();
			for(Session session : sessions) {
				if(now.after(session.getValidUntil())) {
					LOGGER.info("Marking " + session  + " as invalid based on expiry.");
					session.setInvalidatedAt(now);
					session.setValid(false);
				}
			}
		}
		
		@Scheduled(fixedDelay=SESSION_EVICTION_TIME_MS)
		public void evictInValidSessions() {
			if(sessions.isEmpty()) {
				return;
			}
			
			LOGGER.info("Performing session maintenance task: Evicting Invalid Sessions");
			for(Session session : sessions) {
				if(session.isValid()) {
					LOGGER.info("Evicting " + session + " based on invalidity");
					sessions.remove(session);
				}
			}
		}
	}

	@Override
	public Session create() {
		return createWithToken(UUID.randomUUID().toString().replaceAll("-", ""));
	}

	@Override
	public Session createWithToken(String token) {
		Date created = new Date();
		Session session = new Session();
		session.setToken(token);
		session.setCreated(created);
		session.setValid(true);
		session.setValidUntil(Utils.adjustDateByMinutes(created, DEFAULT_EXPIRATION_TIME_MINS));
		sessions.add(session);
		LOGGER.info("Created new " + session);
		return session;
	}

	@Override
	public void evictWithToken(String token) {
		if(!CollectionUtils.isEmpty(sessions)) {
			Date now = new Date();
			for(Session session : sessions) {
				if(!session.isValid() || now.after(session.getValidUntil())) {
					sessions.remove(session);
					LOGGER.info("Removed " + session + " from internal session cache.");
				}
			}
		}
	}

	@Override
	public void invalidateWithToken(String token) {
		if(sessions.isEmpty()) {
			return;
		}
		
		Session session = findByToken(token);
		
		if(session != null) {
			session.setInvalidatedAt(new Date());
			session.setValid(false);
			LOGGER.info("Invalidated " + session);
		}
	}
	
	@Override
	public Session findByToken(String token) {
		assert(StringUtils.isNotBlank(token));
		
		Session foundSession = null;
		
		if(!CollectionUtils.isEmpty(sessions)) {
			for(Session session : sessions) {
				if(token.equals(session.getToken())) {
					LOGGER.info("Found " + session + " matching session token[" + token + "]");
					foundSession = session;
					break;
				}
			}
		}
		
		return foundSession;
	}

	@Override
	public Set<Session> findExpired() {
		Set<Session> expiredSessions = new HashSet<Session>();
		Date now = new Date(System.currentTimeMillis());
		
		if(!CollectionUtils.isEmpty(sessions)) {
			for(Session session : sessions) {
				if(session.getValidUntil().after(now)) {
					expiredSessions.add(session);
				}
			}
		}
		
		LOGGER.info("Found [" + expiredSessions.size() + "] expired sessions.");
		return expiredSessions;
	}

	@Override
	public Set<Session> findValid() {
		Set<Session> validSessions = new HashSet<Session>();
		
		if(!CollectionUtils.isEmpty(sessions)) {
			for(Session session : sessions) {
				if(session.isValid()) {
					validSessions.add(session);
				}
			}
		}
		
		LOGGER.info("Found [" + validSessions.size() + "] expired sessions.");
		return validSessions;
	}

	@Override
	public Set<Session> findAll() {
		return new HashSet<Session>(sessions);
	}

	@Override
	public boolean isSessionValid(Session session) {
		Date now = new Date(System.currentTimeMillis());		
		if(session.isValid() || now.before(session.getValidUntil())) {
			return true;
		}
		return false;
	}

}
