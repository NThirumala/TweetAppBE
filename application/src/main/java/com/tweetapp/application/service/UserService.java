package com.tweetapp.application.service;

import java.util.List;

//import com.tweetapp.application.model.Role;
import com.tweetapp.application.model.User;

public interface UserService {
	User saveUser (User user);
	User getUser(String email);
	List<User> getUsers();
	User updatePassword(User user);
}

