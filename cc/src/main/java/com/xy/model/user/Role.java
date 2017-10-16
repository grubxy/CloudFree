package com.xy.model.user;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(generator="rid")
	@GenericGenerator(name="rid", strategy="assigned")
	private int rid;
	
	private String role;
	
	@ManyToMany(mappedBy="role")
	private Set<User> user;
}
