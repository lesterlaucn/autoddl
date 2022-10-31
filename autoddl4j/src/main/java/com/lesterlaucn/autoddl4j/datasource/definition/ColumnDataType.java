package com.lesterlaucn.autoddl4j.datasource.definition;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by liuyuancheng on 2022/10/31  <br/>
 *
 * @author liuyuancheng
 */
public enum ColumnDataType {
    MySQL_int(Integer.class,11),
    MySQL_bigint(Long.class,20),
    MySQL_datetime(LocalDateTime.class,11),
    MySQL_date(LocalDate.class,11),
    MySQL_double(Double.class,8),
    MySQL_float(Double.class,8),
    MySQL_text(String.class,65535),
    MySQL_tinyint(Integer.class,4),
    MySQL_bool(Boolean.class,1),
    MySQL_decimal(String.class,2),
    MySQL_varchar(String.class,255);

    @Getter
    Class<?> javaType;

    @Getter
    Integer defaultLength;

    ColumnDataType(Class<?> javaType,Integer defaultLength) {
        this.javaType = javaType;
        this.defaultLength = defaultLength;
    }
}
