package com.xy.dao.manage;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Technics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  int tcode;

    private String name;
}
