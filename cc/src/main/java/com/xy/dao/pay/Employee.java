package com.xy.dao.pay;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int eid;

    private String name;

    private int pay;
}
