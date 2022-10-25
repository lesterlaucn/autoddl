package com.lesterlaucn.mybatis.migration.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 全部采用actable自有的注解
 */
@Table
@Data
public class Javax2Swagger2Simple {

    private Long id;

    private String name;

    private Date createTime;

    @Column
    private Boolean isTrue;

    @Column
    private Integer age;

    @Column
    private BigDecimal price;

    @Column
    private String identityCard;

    @Column
    private String shop;

}
