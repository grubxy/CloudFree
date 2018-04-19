package com.xy.service;

import com.xy.domain.User;

import java.util.List;

public interface AuthService {

	User register(User userToAdd);
	
    String login(String username, String password);
    
    String refresh(String oldToken);

    List<User> getUserList() throws Exception;
}
