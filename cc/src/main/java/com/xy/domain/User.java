package com.xy.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

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

	@Getter @Setter private String owner;	// 账户所有者中文名

	@Getter @Setter private String password;

	@Getter @Setter private String email;

	@Getter @Setter private Date lastPasswordResetData;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	name="BH_USER_ROLE",
	joinColumns={@JoinColumn(name="uid")},
	inverseJoinColumns={@JoinColumn(name="rid")})
	@Getter @Setter private List<Role> roles;
}
