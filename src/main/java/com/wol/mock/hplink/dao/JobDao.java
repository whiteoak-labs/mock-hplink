package com.wol.mock.hplink.dao;

import java.util.Set;

import com.wol.mock.hplink.model.Job;

public interface JobDao extends BasicDao<Job> {
	
	/**
	 * Lookup all jobs which the UID property is equal to the value supplied
	 * @param uid
	 * @return Set of all jobs wh
	 */
	public Set<Job> findByUID(String uid);
}
