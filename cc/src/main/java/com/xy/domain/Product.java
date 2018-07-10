package com.xy.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BH_PRODUCT")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idProduct;

    @Column(unique = true)
    @Getter @Setter private String ProductName; // 产品名称

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @Getter
    @JsonIgnore
    @Setter private Set<Seq> seq;            // 工序

}
