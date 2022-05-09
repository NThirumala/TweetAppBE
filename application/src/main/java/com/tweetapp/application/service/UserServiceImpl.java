package com.tweetapp.application.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tweetapp.application.Application;
//import com.tweetapp.application.model.Role;
import com.tweetapp.application.model.User;
//import com.tweetapp.application.repo.RoleRepo;
import com.tweetapp.application.repo.UserRepo;


@Service @Transactional
public class UserServiceImpl implements UserService, UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user == null) {
			logger.info("User not found in Database");
			throw new UsernameNotFoundException("User not found in Database");
		}else {
			
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		logger.info("User logged in successfully");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}
	
	@Override
	public User saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User getUser(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public List<User> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public User updatePassword(User user) {
		String userName = user.getEmail();
		User user1 = userRepo.findByEmail(userName);
		user1.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepo.save(user1);
		return user1;
	}
}
