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
    @Getter
    @Setter
    private int idSeq;

    @Getter @Setter private int seqIndex;   // 工序索引顺序

    @Getter @Setter private String seqName; // 工序名称

    @Getter
    @Setter
    private float seqCost;  // 制作单价

    @Getter
    @Setter
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Material material;  // 工序生成材料

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="BH_SEQ_STAFF",
            joinColumns = {@JoinColumn(name="idSeq")},
            inverseJoinColumns = {@JoinColumn(name="idStaff")}
    )
    private Set<Staff> staffs;
}
