package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="BH_MATERIAL")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter  private Integer idMaterial;

    @Column(unique = true)
    @Getter @Setter private String name;

    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "srcMaterial")
    @JsonIgnore
    private Seq seqSrc;

    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "dstMaterial")
    @JsonIgnore
    private Seq seqDst;
}
