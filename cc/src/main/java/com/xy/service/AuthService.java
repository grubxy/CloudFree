package com.xy.service;

import com.xy.dao.user.User;

public interface AuthService {

	User register(User userToAdd);
	
    String login(String username, String password);
    
    String refresh(String oldToken);
}
