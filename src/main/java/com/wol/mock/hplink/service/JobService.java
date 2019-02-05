package com.wol.mock.hplink.service;

import java.util.Set;

import com.wol.mock.hplink.model.Job;

public interface JobService {

	public Job find(Long id);
	
	public Set<Job> findAll();

	public Set<Job> findByUID(String uid);
	
	public Job save(Job job);
	
	public Job delete(Job entity);
	
	public int deleteAll();

}
