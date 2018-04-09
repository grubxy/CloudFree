package com.xy.domain;

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

    @Getter @Setter private String name;
}
