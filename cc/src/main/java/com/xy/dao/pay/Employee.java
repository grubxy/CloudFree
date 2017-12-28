package com.xy.dao.pay;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer eid;

    private String name;
}
