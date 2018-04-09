package com.xy.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BH_CONSTRUCTION")
public class Construction {
    @Id
    @GeneratedValue(generator = "idConstruct")
    @GenericGenerator(name="idConstruct", strategy = "assigned")
    @Column(length = 20)
    @Getter @Setter private String idConstruct;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Getter @Setter private Date sDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Getter @Setter private Date Date;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @Getter @Setter private Origin origin;      // 生成物料单号

    @ManyToOne
    @JoinColumn(name = "idProduction")
    @Getter @Setter private ProductionFlow production;

    @Enumerated(EnumType.STRING)
    @Getter @Setter private EnumConstructStatus enumConstructStatus;    // 工单状态

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idStaff", nullable = false)
    @Getter @Setter private Staff staff;                                // 施工人员

    @OneToOne(fetch = FetchType.EAGER)
    private Seq seq;

}
