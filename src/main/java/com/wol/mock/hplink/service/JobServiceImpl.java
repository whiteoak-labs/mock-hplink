package com.wol.mock.hplink.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.mock.hplink.dao.JobDao;
import com.wol.mock.hplink.model.Job;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobDao jobDao;
	
	@Override
	public Job find(Long id) {
		return jobDao.find(id);
	}

	@Override
	public Set<Job> findAll() {
		return jobDao.findAll();
	}

	@Override
	public Set<Job> findByUID(String uid) {
		return jobDao.findByUID(uid);
	}

	@Override
	public Job save(Job job) {
		return jobDao.save(job);
	}

	@Override
	public Job delete(Job job) {
		return jobDao.delete(job);
	}

	@Override
	public int deleteAll() {
		return jobDao.deleteAll();
	}

}
