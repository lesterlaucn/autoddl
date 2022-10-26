package com.lesterlaucn.autoddl4j.entities.parser;

import com.lesterlaucn.autoddl4j.database.IndexMethod;
import com.lesterlaucn.autoddl4j.database.TableEngine;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class EntityParserResult {

    @Getter
    @Setter
    private Map<Class<?>, Table> tables;

    public synchronized Table getTable(Class<?> type){
        if (!tables.containsKey(type)){
            this.tables.put(type,new Table());
        }
        return this.getTables().get(type);
    }

    @Data
    public static class Table {
        private String tableNameInLowerCamel;

        private String tableNameInSnake;

        private String comment;

        private List<Column> columns;

        private List<Index> indices;

        private TableEngine engine;

        public Table addColumn(Column columnDef){
            this.columns.add(columnDef);
            return this;
        }
    }

    @Builder
    public static class Column {

        /**
         * 是否是主键
         */
        private Boolean isPrimaryKey;

        private Class<?> javaType;

        private String columnNameInLowerCamel;

        private String columnNameInSnake;

        private Integer length = 255;

        /**
         * 备注
         */
        private String comment = "";

        /**
         * 默认值
         */
        private String defaultValue = "";

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
    @Builder
    public static class Index {

        /**
         * 索引名称
         */
        private String indexName;

        /**
         * 索引字段（有序）
         */
        private List<Column> columns;

        /**
         * 索引方法
         */
        private IndexMethod indexMethod;

        private String comment;
    }
}
