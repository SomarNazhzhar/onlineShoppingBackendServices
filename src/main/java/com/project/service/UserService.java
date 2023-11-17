package com.project.service;

import com.project.model.User;

import java.util.List;

public interface UserService {
	
	public User saveUer(User user);
	
	public User updateUserCredential(User user,String key);
	
	public String userLogout(String key);

	public List<User> getAllUsers();
	//public List<Address> viewAllAddress();
}
