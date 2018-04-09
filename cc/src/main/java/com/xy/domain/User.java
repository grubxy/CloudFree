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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="BH_USER")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter
	@Setter
	private int uid;
	
	@Column(unique=true)
	@Getter @Setter private String username;

	@Getter @Setter private String password;

	@Getter @Setter private String email;

	@Getter @Setter private Date lastPasswordResetData;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	name="BH_USER_ROLE",
	joinColumns={@JoinColumn(name="uid")},
	inverseJoinColumns={@JoinColumn(name="rid")})
	@Getter @Setter private Set<Role> role;
}
