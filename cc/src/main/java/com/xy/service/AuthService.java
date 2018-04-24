package com.xy.service;

import com.xy.domain.User;

import java.util.List;

public interface AuthService {

	User register(User userToAdd) throws Exception;
	
    String login(String username, String password);
    
    String refresh(String oldToken);

    List<User> getUserList(int page, int size) throws Exception;

    void deleteUser(Integer id) throws Exception;
}
