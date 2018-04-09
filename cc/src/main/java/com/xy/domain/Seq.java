package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BH_SEQ")
public class Seq {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idSeq;

    @Getter @Setter private int seqIndex;   // 工序索引顺序

    @Getter @Setter private String seqName; // 工序名称

    @Getter @Setter private float seqCost;  // 制作单价

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Getter @Setter private Material material;  // 工序生成材料


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="BH_SEQ_STAFF",
            joinColumns = {@JoinColumn(name="idSeq")},
            inverseJoinColumns = {@JoinColumn(name="idStaff")}
    )
    @Getter @Setter private Set<Staff> staffs;
}
