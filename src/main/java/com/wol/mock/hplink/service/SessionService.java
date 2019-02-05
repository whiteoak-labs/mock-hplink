package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Session;

/**
 * Represents an internal session which does not get 
 * persisted and is managed within the scope of the application 
 * at runtime.
 * 
 * @author tcook
 *
 */
public interface SessionService {
	
	public static final int DEFAULT_EXPIRATION_TIME_MINS = 90;
	public static final long SESSION_MAINTENANCE_DELAY_MS = ((90*60) * 1000);
	public static final long SESSION_EVICTION_TIME_MS = ((180*60) * 1000);
	public static final long SESSION_VALIDATION_TIME_MS = ((10*60) * 1000);

	public Session create();
	
	public Session createWithToken(String token);
	
	public void evictWithToken(String token);
	
	public void invalidateWithToken(String token);
	
	public Session findByToken(String token);
	
	public Set<Session> findExpired();
	
	public Set<Session> findValid();
	
	public Set<Session> findAll();
	
	public boolean isSessionValid(Session session);
}
