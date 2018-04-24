package com.xy.service.impl;

import com.xy.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.xy.security.JwtTokenUtil;
import com.xy.security.JwtUser;
import com.xy.service.AuthService;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
    private AuthenticationManager authenticationManager;
   
    private UserDetailsService userDetailsService;   
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            UserRepository userRepository,
            RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User userToAdd) throws Exception{
        final String username = userToAdd.getUsername();
        if(userRepository.findByUsername(username)!=null) {
            throw new UserException(ErrorCode.USER_USERNAME_EXIST.getCode(), ErrorCode.USER_USERNAME_EXIST.getMsg());
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        final String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setLastPasswordResetData(new Date());

        return userRepository.save(userToAdd);
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String token = jwtTokenUtil.generateToken(userDetails);
        return token;
    }

    @Override
    public String refresh(String oldToken) {
        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())){
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public List<User> getUserList(int page, int size) throws Exception {
        Long total = (size != 0)?size:userRepository.count();
        PageRequest pageRequest = new PageRequest(page, total.intValue());
        List<User> userList = userRepository.findAll(pageRequest).getContent();
        return userList;
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        userRepository.delete(id);
    }

}
