package com.xy.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mcode;

    private String name;

    private String spec;
}
