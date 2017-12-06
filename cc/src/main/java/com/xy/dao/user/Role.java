package com.xy.dao.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
}
