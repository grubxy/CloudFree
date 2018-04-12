package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "BH_SEQ")
public class Seq implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idSeq;

    @Getter @Setter private int seqIndex;   // 工序索引顺序

    @Getter @Setter private String seqName; // 工序名称

    @Getter @Setter private float seqCost;  // 制作单价

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Getter @Setter
    @JsonIgnore
    private Material srcMaterial;  // 工序源材料

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @Getter @Setter
    @JsonIgnore
    private Material dstMaterial;  // 工序生成


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="BH_SEQ_STAFF",
            joinColumns = {@JoinColumn(name="idSeq")},
            inverseJoinColumns = {@JoinColumn(name="idStaff")}
    )
    @JsonIgnore
    @Getter @Setter private Set<Staff> staffs;
}
