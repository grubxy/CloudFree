package com.xy.entity;

import com.xy.entity.Material;
import com.xy.entity.Technics;
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


}
