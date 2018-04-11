package com.xy.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

// 生产流程的每个工序详情
@Entity
@Table(name="BH_SEQINFO")
public class SeqInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter private int idSeqInfo;

    @OneToOne(fetch = FetchType.EAGER)
    @Getter @Setter private Seq seq;

    @Getter @Setter private int dstCounts;

    @Getter @Setter private int doingCounts;

    @Getter @Setter private int cmplCounts;

    @Getter @Setter private int errCounts;
}
