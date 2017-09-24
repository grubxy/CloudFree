package com.xy.security;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser implements UserDetails {

	private final String id;
	private final String username;
	private final String password;
	private final String email;
	private final Date lastPasswordResetDate;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public JwtUser(
				String id,
				String username,
				String password,
				String email,
				Collection<? extends GrantedAuthority> authorities,
				Date lastPasswordResetDate) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.lastPasswordResetDate = lastPasswordResetDate;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	@JsonIgnore
	public String getId() {
		return id;
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	public String getEmail() {
		return email;
	}
	
	@JsonIgnore
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

}
