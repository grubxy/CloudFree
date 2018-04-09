package com.xy.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "BH_ORIGIN")
public class Origin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idOrigin;

    private String name;

    private int counts;

}
