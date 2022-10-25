package com.lesterlaucn.autoddl4j.entities.parser;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class EntityParserResult {

    @Getter
    @Setter
    private List<Table> tables;

    @Data
    public static class Table {
        private String tableNameInLowerCamel;
        private String tableNameInSnake;
        private String comment;

        private List<Column> columns;

        private List<Index> indices;
    }

    @Data
    @Accessors(chain = true)
    public static class Column {

        private Class<?> javaType;

        private String columnNameInLowerCamel;

        private String columnNameInSnake;

        private Integer length = 255;

        /**
         * 备注
         */
        private String comment = "";

        /**
         * 是否唯一
         */
        private Boolean unique = false;

        /**
         * decimal的总长度
         */
        private Integer decimalPrecision;

        /**
         * decimal类型小数精度
         */
        private Integer decimalScale;

    }

    /**
     * 索引
     */
    @Data
    public static class Index {
        private String indexName;
    }
}
