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
    MySQL_int(Integer.class),
    MySQL_bigint(Long.class),
    MySQL_datetime(LocalDateTime.class),
    MySQL_date(LocalDate.class),
    MySQL_double(Double.class),
    MySQL_float(Double.class),
    MySQL_text(String.class),
    MySQL_tinyint(Integer.class),
    MySQL_boolean(Boolean.class),
    MySQL_decimal(String.class),
    MySQL_varchar(String.class);

    @Getter
    Class<?> javaType;

    ColumnDataType(Class<?> javaType) {
        this.javaType = javaType;
    }
}
