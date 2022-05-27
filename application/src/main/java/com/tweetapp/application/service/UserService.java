package com.tweetapp.application.service;

import java.util.List;
import java.util.Map;

//import com.tweetapp.application.model.Role;
import com.tweetapp.application.model.User;

public interface UserService {
	Map<String, String> saveUser (User user);
	User getUser(String email);
	List<User> getUsers();
	User updatePassword(User user);
}

