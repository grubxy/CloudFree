package com.xy.entity;

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

import com.xy.entity.Role;
import lombok.Data;

@Data
@Entity
@Table(name="user")
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
	name="user_role",
	joinColumns={@JoinColumn(name="uid")},
	inverseJoinColumns={@JoinColumn(name="rid")})
	private Set<Role> role;
}
