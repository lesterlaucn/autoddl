package tech.mozhou.autoddl4j;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import tech.mozhou.autoddl4j.target.definition.CharacterSet;
import tech.mozhou.autoddl4j.target.definition.Collation;
import tech.mozhou.autoddl4j.target.definition.IndexMethod;
import tech.mozhou.autoddl4j.target.definition.TableEngine;
import tech.mozhou.autoddl4j.target.definition.type.DbType;
import tech.mozhou.autoddl4j.target.definition.type.JavaType2Column;
import tech.mozhou.autoddl4j.util.JsonUtil;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by liuyuancheng on 2022/10/25  <br/>
 *
 * @author liuyuancheng
 */
public class TableDef implements Serializable {

    private final Map<String, Table> tables = Maps.newHashMap();

    private final Map<String, String> tableEntityMap = Maps.newHashMap();

    private TableDef() {
    }

    public static TableDef create() {
        return new TableDef();
    }

    public Table getTable(@NonNull String tableName) {
        return getTable(tableName, null);
    }

    public List<String> getTableNames() {
        return Lists.newArrayList(tables.keySet());
    }

    /**
     * 创建并获取Table
     *
     * @param tableName
     * @param packageScan
     * @return
     */
    public synchronized Table getTable(@NonNull String tableName, String packageScan) {
        if (!tables.containsKey(tableName)) {
            this.tables.put(tableName, new Table());
            this.tableEntityMap.put(tableName, StringUtils.isEmpty(packageScan) ? "" : packageScan);
        }
        return this.tables.get(tableName);
    }

    /**
     * 获取表名对应的Entity名称
     *
     * @param tableName 表名
     * @return 类的全限定名
     */
    public String getTableMappedType(@NonNull String tableName) {
        return this.tableEntityMap.getOrDefault(tableName, null);
    }

    /**
     * 表格的定义
     */
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

        private List<Column> primaryColumns = Lists.newArrayList();

        public Table addColumn(Column columnDef) {
            this.columns.put(columnDef.getColumnName(), columnDef);
            return this;
        }

        public String toCreateDdl() {
            StringBuilder sb = new StringBuilder();
            sb.append("\nCREATE TABLE `").append(this.tableName).append("` (\n");
            for (Map.Entry<String, Column> column : columns.entrySet()) {
                sb.append(' ').append(column.getValue().toCreateDdl());
                if (column.getValue().isPrimaryKey){
                    primaryColumns.add(column.getValue());
                }
            }
            if (!primaryColumns.isEmpty()){
                final String key = primaryColumns.stream().map(Column::getColumnName).collect(Collectors.joining(","));
                sb.append(" ").append("PRIMARY KEY (").append(key).append(")\n");
            }
            sb.append(") ENGINE=").append(this.engine.getName());
            sb.append(" DEFAULT CHARSET=").append(this.characterSet.getName());
            return sb.toString();
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

        public String toCreateDdl() {
            StringBuilder sb = new StringBuilder();
            sb.append(columnName);
            final JavaType2Column type2Column = JavaType2Column.getType(this.javaType);
            Objects.requireNonNull(type2Column,this.javaType.getName());
            sb.append(" ").append(type2Column.toDefinition()).append(" ");
            if (!isNullable){
                sb.append("NOT NULL ");
            }
            if (StringUtils.isNotBlank(this.comment)){
                sb.append("COMMENT ").append(StringUtils.wrap(comment,'"'));
            }
            sb.append(",").append("\n");
            return sb.toString();
        }
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
