package com.xy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "BH_SEQ")
public class Seq {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSeq;

    private int seqIndex;   // 工序索引顺序

    private String seqName; // 工序名称

    private float seqCost;  // 制作单价

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name="SEQ_STAFF",
            joinColumns = {@JoinColumn(name="idSeq")},
            inverseJoinColumns = {@JoinColumn(name="idStaff")}
    )
    private Set<Staff> staffs;
}
