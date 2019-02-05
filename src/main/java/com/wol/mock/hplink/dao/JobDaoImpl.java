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

import com.wol.mock.hplink.model.Job;

@Repository
public class JobDaoImpl implements JobDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(JobDaoImpl.class);
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Job find(Long id) {
		assert(id != null);
		Query findQuery = entityManager.createQuery("FROM Job WHERE id=:id");
		findQuery.setParameter("id", id);
		Job job = null;
		
		try {
			job = (Job) findQuery.getSingleResult();
		}
		catch(NoResultException e) {}
		
		return job;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Job> findAll() {
		Query findQuery = entityManager.createQuery("FROM Job");
		return (Set<Job>) new HashSet<Job>(findQuery.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<Job> findByUID(String uid) {
		assert(StringUtils.isNotBlank(uid));
		Query findQuery = entityManager.createQuery("FROM Job WHERE uid=:uid");
		findQuery.setParameter("uid", uid);
		return (Set<Job>)new HashSet<Job>(findQuery.getResultList());
	}

	@Override
	@Transactional
	public Job save(Job job) {
		assert(job != null);
		Job existing = find(job.getId());
		
		if(existing != null) {
			job = entityManager.merge(job);
		}
		else {
			entityManager.persist(job);
		}
		
		entityManager.flush();
		LOGGER.info("Successfully saved job: " + job);
		return job;
	}

	@Override
	@Transactional
	public Job delete(Job job) {
		assert(job != null);
		entityManager.remove(job);
		entityManager.flush();
		LOGGER.debug("Successfully deleted " + job);
		return job;
	}

	@Override
	@Transactional
	public int deleteAll() {
		Set<Job> jobs = findAll();
		int count = 0;
		
		if(!CollectionUtils.isEmpty(jobs)) {
			for(Job job : jobs) {
				entityManager.remove(job);
				count++;
			}
			
			entityManager.flush();
			LOGGER.debug("Successfully deleted all jobs");
		}
		
		return count;
	}

}
