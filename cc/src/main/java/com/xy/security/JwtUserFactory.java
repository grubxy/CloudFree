package com.xy.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xy.model.user.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
		
	}
	
	public static JwtUser create(User user) {
		return new JwtUser(
				user.getId(),
				user.getUsername(),
				user.getPassword(),
				user.getEmail(),
				mapToGrantedAuthorities(user.getRoles()),
				user.getLastPasswordResetData());
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		  return authorities.stream()
	                .map(SimpleGrantedAuthority::new)
	                .collect(Collectors.toList());
	}
}
