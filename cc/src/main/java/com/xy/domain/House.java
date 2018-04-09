package com.xy.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "BH_HOUSE")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idHouse;

    private String name;

    @OneToMany
    @JoinColumn(name = "idHouse")
    private Set<Origin> origins;
}
