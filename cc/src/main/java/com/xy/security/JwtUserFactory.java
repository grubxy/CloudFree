package com.xy.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xy.domain.Role;
import com.xy.domain.User;

public final class JwtUserFactory {

	private JwtUserFactory() {
		
	}
	
	public static JwtUser create(User user) {
		return new JwtUser(
				user.getUid(),
				user.getUsername(),
				user.getPassword(),
				user.getEmail(),
				mapToGrantedAuthorities(user.getRoles()),
				user.getLastPasswordResetData());
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> set) {
		List<String> roles = new ArrayList<String>();
		for(Role temp: set) {
			roles.add(temp.getRole());
		}
		return roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
	}
}
