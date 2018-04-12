package com.xy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

// 生产流程实体类
@Entity
@Table(name = "BH_PRODUCTIONFLOW")
public class ProductionFlow implements Serializable{

    @Id
    @GeneratedValue(generator = "idProduction")
    @GenericGenerator(name="idProduction", strategy = "assigned")
    @Column(length = 20)
    @Getter @Setter private String idProduction;

    @Getter @Setter private Integer dstCounts;

    @Getter @Setter private Integer factCounts;

    @Getter @Setter private Integer cmplCounts;

    @Getter @Setter private  Integer errCounts;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Getter @Setter private Date date;

    @Getter @Setter private String detail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idProduct", nullable = false)
    @Getter @Setter private Product product;            // 产品

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "idProduction")
    @JsonIgnore
    @Getter @Setter private Set<SeqInfo> seqInfo;            // 对应产品工序的详情

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="idProduction")
    @JsonIgnore
    @Getter @Setter private Set<Construction> constructs;  // 工单

}
