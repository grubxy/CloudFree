package com.xy.dao.produce;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table
public class Production {
    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name="id", strategy = "assigned")
    private Long id;

    private String name;

    private Integer dst_counts;

    private Integer fact_counts;

    private Integer cmpl_counts;

    private  Integer err_counts;

    private  float state;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date;

    private String detail;

}
