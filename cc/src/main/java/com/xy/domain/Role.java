package com.xy.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name="BH_ROLE")
public class Role {

	@Id
	@GeneratedValue(generator="rid")
	@GenericGenerator(name="rid", strategy="assigned")
	@Getter @Setter private int rid;

	@Getter @Setter private String role;
}
