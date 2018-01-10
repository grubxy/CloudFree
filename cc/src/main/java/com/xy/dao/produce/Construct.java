package com.xy.dao.produce;

import com.xy.dao.manage.Material;
import com.xy.dao.manage.Technics;
import com.xy.dao.pay.Employee;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table
public class Construct {
    @Id
    @GeneratedValue(generator = "cid")
    @GenericGenerator(name="cid", strategy = "assigned")
    @Column(length = 20)
    private String cid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date Date;

    private Integer err_counts;

    private Integer dst_counts;

    private Integer cmpl_counts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "eid")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mcode")
    private Material material;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tcode")
    private Technics technics;

}
