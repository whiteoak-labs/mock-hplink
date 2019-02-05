package com.wol.mock.hplink.dao;

import com.wol.mock.hplink.model.User;

public interface UserDao extends BasicDao<User> {
	
	public User findByEmail(String email);
}
