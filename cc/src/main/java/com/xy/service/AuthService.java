package com.xy.service;

import com.xy.entity.User;

public interface AuthService {

	User register(User userToAdd);
	
    String login(String username, String password);
    
    String refresh(String oldToken);
}
