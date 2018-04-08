package com.xy.entity;

import lombok.Data;

import javax.persistence.*;

// 生产流程的每个工序详情
@Data
@Entity
@Table(name="BH_SEQINFO")
public class SeqInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idSeqInfo;

    private int seqIndex;

    private int dstCounts;

    private int cmplCounts;

    private int errCounts;
}
