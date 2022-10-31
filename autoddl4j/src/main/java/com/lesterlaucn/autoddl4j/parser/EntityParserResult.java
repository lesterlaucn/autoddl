package com.lesterlaucn.autoddl4j.parser;

import com.google.common.collect.Maps;
import com.lesterlaucn.autoddl4j.datasource.definition.*;
import com.lesterlaucn.autoddl4j.parser.entity.util.JsonUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.collections.map.LinkedMap;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class EntityParserResult implements Serializable {

    private Map<String, Table> tables;

    private EntityParserResult() {
        this.tables = Maps.newHashMap();
    }

    public static EntityParserResult create() {
        return new EntityParserResult();
    }

    /**
     * 创建并获取Table
     *
     * @param type
     * @return
     */
    public synchronized Table getTable(String type) {
        if (!tables.containsKey(type)) {
            this.tables.put(type, new Table());
        }
        return this.tables.get(type);
    }

    @Data
    @Accessors(chain = true)
    public static class Table implements Serializable {

        private String tableName;

        private String[] formerName;

        private String tableNameOriginal;

        private String comment;

        private DbType dataBaseType;

        private CharacterSet characterSet;

        private Collation collation;

        /**
         * fieldName->ColumnDef
         */
        private Map<String, Column> columns = new LinkedHashMap<>();

        private List<Index> indices;

        private TableEngine engine;

        public Table addColumn(Column columnDef) {
            this.columns.put(columnDef.getColumnName(), columnDef);
            return this;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Column implements Serializable {

        /**
         * 是否是主键
         */
        private Boolean isPrimaryKey;

        private Class<?> javaType;

        private String columnOriginal;

        private String columnName;

        private Integer length;

        private Integer ordinalPosition;

        private Boolean isNullable;

        /**
         * 备注
         */
        private String comment = "";

        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 是否唯一
         */
        private Boolean unique = false;

        /**
         * decimal类型小数精度
         */
        private Integer decimalScale;

    }

    /**
     * 索引
     */
    @Data
    @Accessors(chain = true)
    public static class Index implements Serializable {

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

    @Override
    public String toString() {
        return JsonUtil.toJsonPrettyStr(this.tables);
    }
}
