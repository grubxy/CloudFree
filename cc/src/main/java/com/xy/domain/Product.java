package com.xy.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BH_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idProduct;

    @Getter @Setter private String ProductName; // 产品名称


    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name="idProduct")
    @Getter @Setter private Set<Seq> seq;            // 工序

}
