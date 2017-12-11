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
public class Construct {
    @Id
    @GeneratedValue(generator = "id")
    @GenericGenerator(name="id", strategy = "assigned")
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date Date;

    private Integer err_counts;

    private Integer dst_counts;

    private Integer cmpl_counts;

}
