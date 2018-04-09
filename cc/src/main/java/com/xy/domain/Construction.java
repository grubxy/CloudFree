package com.xy.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "BH_CONSTRUCTION")
public class Construction {
    @Id
    @GeneratedValue(generator = "idConstruct")
    @GenericGenerator(name="idConstruct", strategy = "assigned")
    @Column(length = 20)
    private String idConstruct;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date Date;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Origin origin;      // 生成物料单号

    @ManyToOne
    @JoinColumn(name = "idProduction")
    private Production production;

    @Enumerated(EnumType.STRING)
    private EnumConstructStatus enumConstructStatus;    // 工单状态

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStaff", nullable = false)
    private Staff staff;                                // 施工人员


}
