package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Getter @Setter private int idStaff;

    @Column(unique = true)
    @Getter @Setter private String staffName;   // 员工姓名

    @Getter @Setter private String staffPhone;  // 员工手机号码

    @ManyToMany(fetch = FetchType.LAZY, mappedBy="staffs")
    @Getter
    @Setter
    @JsonIgnore
    private Set<Seq> seqs;       // 工序

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "idStaff")
    @Getter
    @Setter
    @JsonIgnore
    private Set<Construction> constructs;  // 工单

    @Convert(converter = EnumStaffStatus.Convert.class)
    @Getter @Setter private EnumStaffStatus enumStaffStatus;    // 员工在职状态
}
