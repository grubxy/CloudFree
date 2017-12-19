package com.xy.dao.produce;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table
public class Production {
    @Id
    @GeneratedValue(generator = "pid")
    @GenericGenerator(name="pid", strategy = "assigned")
    @Column(length = 8)
    private String pid;

    private String name;

    private Integer dst_counts;

    private Integer fact_counts;

    private Integer cmpl_counts;

    private  Integer err_counts;

    private  float state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String detail;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name="pid")
    private Set<Construct> constructs;

}
