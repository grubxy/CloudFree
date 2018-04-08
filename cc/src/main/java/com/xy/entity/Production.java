package com.xy.entity;

import com.xy.entity.Construct;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

// 生产流程实体类
@Data
@Entity
@Table(name = "BH_PRODUCTION")
public class Production {
    @Id
    @GeneratedValue(generator = "idProduction")
    @GenericGenerator(name="idProduction", strategy = "assigned")
    @Column(length = 20)
    private String idProduction;

    private Integer dstCounts;

    private Integer factCounts;

    private Integer cmplCounts;

    private  Integer errCounts;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String detail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="idProduct", nullable = false)
    private Product product;

}
