package com.xy.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "BH_STAFF")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idStaff;

    private String staffName;   // 员工姓名

    @ManyToMany(fetch = FetchType.EAGER, mappedBy="staffs")
    private Set<Seq> seqs;       // 工序
}
