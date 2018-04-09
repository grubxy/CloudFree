package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BH_STAFF")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int idStaff;

    @Getter
    @Setter
    private String staffName;   // 员工姓名

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, mappedBy="staffs")
    private Set<Seq> seqs;       // 工序

    @Getter
    @Setter
    @OneToMany
    @JoinColumn(name = "idStaff")
    private Set<Construction> constructs;  // 工单

    @Getter
    @Setter
    private EnumStaffStatus enumStaffStatus;    // 员工在职状态
}
