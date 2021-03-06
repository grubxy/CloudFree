package com.xy.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="BH_ROLE")
public class Role implements Serializable{

	@Id
	@GeneratedValue(generator="rid")
	@GenericGenerator(name="rid", strategy="assigned")
	@Getter @Setter private int rid;

	@Getter @Setter private String role;

	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	@JsonIgnore
	@Getter @Setter private List<User> users;
}
