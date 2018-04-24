package com.xy.controller;

import javax.servlet.http.HttpServletRequest;

import com.xy.domain.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.xy.domain.User;
import com.xy.security.JwtAuthenticationRequest;
import com.xy.security.JwtAuthenticationResponse;
import com.xy.service.AuthService;

import java.util.List;


@RestController
public class AuthController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public String createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException{
        final String token = authService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        // Return the token
        return token;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
            HttpServletRequest request) throws AuthenticationException{
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if(refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User register(@RequestBody User addedUser) throws Exception {
    	return authService.register(addedUser);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> getUserList(@RequestParam("page") int page, @RequestParam("size") int size) throws Exception {
        return authService.getUserList(page, size);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") Integer id) throws Exception {
        authService.deleteUser(id);
    }
}
