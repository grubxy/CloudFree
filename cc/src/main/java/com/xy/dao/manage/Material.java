package com.xy.dao.manage;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int code;

    private String name;

    private String spec;
}
