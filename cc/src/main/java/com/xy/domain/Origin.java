package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "BH_ORIGIN")
public class Origin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idOrigin;

    @Getter @Setter
    @Column(unique = true)
    private String name;

    @Getter @Setter private int counts;

}
