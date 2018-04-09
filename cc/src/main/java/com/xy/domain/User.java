package com.xy.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="BH_USER")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int uid;
	
	@Column(unique=true)
	private String username;
	
	private String password;
	
	private String email;
	
	private Date lastPasswordResetData;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	name="BH_USER_ROLE",
	joinColumns={@JoinColumn(name="uid")},
	inverseJoinColumns={@JoinColumn(name="rid")})
	private Set<Role> role;
}
