package com.lesterlaucn.autoddl4j.entities.parser;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lesterlaucn.autoddl4j.datasource.definition.*;
import com.lesterlaucn.autoddl4j.entities.parser.util.JsonUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class EntityParserResult implements Serializable{

    public static EntityParserResult create(){
        return new EntityParserResult();
    }

    @Getter
    @Setter
    private Map<Class<?>, Table> tables = Maps.newHashMap();

    public synchronized Table getTable(Class<?> type){
        if (!tables.containsKey(type)){
            this.tables.put(type,new Table());
        }
        return this.getTables().get(type);
    }

    @Data
    @Accessors(chain = true)
    public static class Table implements Serializable{

        private String tableName;

        private String[] formerName;

        private String tableNameOriginal;

        private String comment;

        private DbType dataBaseType;

        private CharacterSet characterSet;

        private Collation collation;

        private List<Column> columns = Lists.newArrayList();

        private List<Index> indices;

        private TableEngine engine;

        public Table addColumn(Column columnDef){
            this.columns.add(columnDef);
            return this;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Column implements Serializable{

        /**
         * 是否是主键
         */
        private Boolean isPrimaryKey;

        private Class<?> javaType;

        private String columnOriginal;

        private String columnName;

        private Integer length = 255;

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
        return JsonUtil.toJsonStr(this);
    }
}
