package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BH_HOUSE")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idHouse;

    private String name;

    @OneToMany
    @JoinColumn(name = "idHouse")
    @Getter @Setter private Set<Origin> origins;
}
