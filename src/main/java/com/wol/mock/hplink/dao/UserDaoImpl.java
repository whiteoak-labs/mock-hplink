package com.wol.mock.hplink.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.wol.mock.hplink.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public User find(Long id) {
		assert(id != null);
		Query findQuery = entityManager.createQuery("FROM User WHERE id=:id");
		findQuery.setParameter("id", id);
		User user = null;
		
		try {
			user = (User)findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<User> findAll() {
		Query findQuery = entityManager.createQuery("FROM User");
		return new HashSet<User>(findQuery.getResultList());
	}

	@Override
	public User findByEmail(String email) {
		Query findQuery = entityManager.createQuery("From User WHERE email=:email");
		findQuery.setParameter("email", email);
		User user = null;
		
		try {
			user = (User)findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return user;
	}
	
	@Override
	@Transactional
	public User save(User user) {
		assert(user != null);
		User existing = findByEmail(user.getEmail());
		
		if(existing != null) {
			user = entityManager.merge(user);
		}
		else {
			entityManager.persist(user);
		}
		
		entityManager.flush();
		LOGGER.debug("Successfully saved " + user);
		return user;
	}

	@Override
	@Transactional
	public User delete(User user) {
		assert(user != null);
		entityManager.remove(user);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + user);
		return user;
	}

	@Override
	@Transactional
	public int deleteAll() {
		Set<User> users = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(users)) {
			for(User event : users) {
				entityManager.remove(event);
				count++;
			}
			
			entityManager.flush();
			LOGGER.debug("Successfully deleted all users");
		}
		
		return count;
	}
}
