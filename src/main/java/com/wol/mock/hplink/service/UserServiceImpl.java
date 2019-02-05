package com.wol.mock.hplink.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wol.mock.hplink.Utils;
import com.wol.mock.hplink.dao.UserDao;
import com.wol.mock.hplink.model.User;
import com.wol.mock.hplink.model.User.Role;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private SessionService sessionService;

	@PostConstruct
	public void createAdminUser() {
		User adminUser = findByEmail("admin.sims@wol.com");
		
		if(adminUser == null) {
			adminUser = create("Administrator", "MHPLink", 
					"master", "admin.sims@wol.com", User.Role.ADMIN);
			save(adminUser);
		}
	}
	
	@Override
	public User find(long id) {
		return userDao.find(id);
	}

	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public Set<User> findLoggedin() {
		Set<User> allUsers = userDao.findAll();
		if(allUsers == null || allUsers.isEmpty()) {
			return allUsers;
		}
		
		Set<User> loggedInUsers = new HashSet<User>();
		for(User user : allUsers) {
			if(User.State.LOGGED_IN.equals(user.getState())) {
				loggedInUsers.add(user);
			}
		}
		
		return loggedInUsers;
	}

	@Override
	public User save(User user) {
		return userDao.save(user);
	}

	@Override
	public User delete(User user) {
		return userDao.delete(user);
	}

	@Override
	public User create(String firstName, String lastName, String password, String email, Role role) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setCreated(new Date());
		user.setEmail(email);
		user.setPassword(Utils.Crypto.encrypt(password));
		user.setRole(role);
		return user;
	}

	@Override
	public void login(User user) {
		user.setState(User.State.LOGGED_IN);
		user.setLastLogin(new Date());
		user.setSession(sessionService.create());
		save(user);
	}

	@Override
	public void logout(User user) {
		user.setState(User.State.LOGGED_OUT);
		save(user);
	}

}
