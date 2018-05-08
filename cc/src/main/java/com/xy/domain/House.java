package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Getter @Setter
    private String houseName;

    @Getter @Setter
    private String houseDesc;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "idHouse")
    @JsonIgnore
    @Getter @Setter private Set<Origin> origins;
}
