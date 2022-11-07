package tech.mozhou.autoddl4j.target.ddlparser.impl;


import com.google.common.collect.Lists;
import tech.mozhou.autoddl4j.TableDef;
import tech.mozhou.autoddl4j.target.ddlparser.ITableDdlParser;
import tech.mozhou.autoddl4j.target.definition.CharacterSet;
import tech.mozhou.autoddl4j.target.definition.DbType;
import tech.mozhou.autoddl4j.target.definition.TableEngine;
import tech.mozhou.autoddl4j.target.definition.type.ColumnType2Java;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 * 每次处理一张表(MySQL)
 *
 * @author liuyuancheng
 */
@Slf4j
public class MySqlTableDdlParser implements ITableDdlParser {

    private static final String CREATE_TABLE = "CREATE TABLE";

    private static final String TABLE_ENGINE_PREFIX = "MySQL_";

    /**
     * ddl语句
     */
    private String ddl;

    /**
     * 主键列表
     */
    private List<String> primaryKeys = Lists.newArrayList();

    private TableDef result = TableDef.create();

    public MySqlTableDdlParser(String ddl) {
        this.ddl = ddl;
    }

    @Override
    public TableDef parse() {
        parsePrimaryKeys();
        final StringTokenizer tokenizer = new StringTokenizer(ddl, "\n");
        TableDef.Table currentTable = null;
        boolean isColumnDefinition = false;
        // 列的位置顺序
        Integer columnOrdinalPosition = 1;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            if (StringUtils.isBlank(token)) continue;
            // 处理表(开始)
            final String tableName = parseTableStart(token);
            if (!isColumnDefinition && StringUtils.isNotEmpty(tableName)) {
                currentTable = this.result.getTable(tableName, "");
                currentTable.setTableName(tableName);
                currentTable.setFormerName(new String[]{tableName});
                isColumnDefinition = true;
                continue;
            }
            // 处理表(结束)
            if (isColumnDefinition && parseTableEnd(token, currentTable)) {
                isColumnDefinition = false;
            }
            // 处理字段
            if (isColumnDefinition) {
                parseColumn(token, columnOrdinalPosition, currentTable);
                columnOrdinalPosition++;
            }
        }
        return result;
    }

    private void parsePrimaryKeys() {
        Pattern pattern = Pattern.compile("PRIMARY KEY \\((.*?)\\)");
        Matcher matcher = pattern.matcher(ddl);
        if (matcher.find()) {
            final String match = matcher.toMatchResult().group(1);
            for (String s : match.split(",")) {
                this.primaryKeys.add(StringUtils.unwrap(s, "`"));
            }
        }
    }

    /**
     * 处理表字段
     *
     * @param token
     * @param columnOrdinalPosition
     * @param currentTable
     */
    private void parseColumn(String token, Integer columnOrdinalPosition, TableDef.Table currentTable) {
        final TableDef.Column column = new TableDef.Column();
        token = token.trim();
        Pattern pattern = null;
        // 匹配字段名
        Matcher matcher = null;
        pattern = Pattern.compile("^`([a-zA-Z0-9_]+)`");
        matcher = pattern.matcher(token);
        if (!matcher.find()) {
            return;
        }
        column.setColumnName(matcher.toMatchResult().group(1));
        column.setIsNullable(!token.contains("NOT NULL"));
        column.setOrdinalPosition(columnOrdinalPosition);
        // 匹配默认值
        pattern = Pattern.compile("DEFAULT '(.*?)'");
        matcher = pattern.matcher(token);
        if (matcher.find()) {
            column.setDefaultValue(matcher.toMatchResult().group(1));
        }
        // 匹配字段注释
        pattern = Pattern.compile("COMMENT '(.*?)'");
        matcher = pattern.matcher(token);
        if (matcher.find()) {
            column.setComment(matcher.toMatchResult().group(1));
        }
        // 字段类型解析
        pattern = Pattern.compile("` ([a-z]{2,})[ (]");
        matcher = pattern.matcher(token);
        if (matcher.find()) {
            final String dataType = matcher.toMatchResult().group(1);
            column.setJavaType(ColumnType2Java.valueOf(TABLE_ENGINE_PREFIX + dataType).getJavaType());
        }
        // 字段类型长度
        pattern = Pattern.compile("` ([a-z]{2,})(\\(([0-9]*)\\))?");
        matcher = pattern.matcher(token);
        if (matcher.find()) {
            final String dataType = matcher.toMatchResult().group(1);
            final String length = matcher.toMatchResult().group(3);
            if (Objects.nonNull(length)) {
                column.setLength(Integer.parseInt(length));
            }
            if (Objects.isNull(column.getLength())) {
                column.setLength(ColumnType2Java.valueOf(TABLE_ENGINE_PREFIX + dataType).getDefaultLength());
            }
            log.debug("字段{} 长度为 {}", column.getColumnName(), column.getLength());
        }
        // 主键判断
        if (this.primaryKeys.contains(column.getColumnName())) {
            column.setIsPrimaryKey(true);
        } else {
            column.setIsPrimaryKey(false);
        }
        currentTable.addColumn(column);
    }

    /**
     * DDL表定义结束
     *
     * @param token
     * @param currentTable
     * @return
     */
    private boolean parseTableEnd(String token, TableDef.Table currentTable) {
        if (StringUtils.startsWith(token.trim(), ")")) {
            Pattern pattern = null;
            Matcher matcher = null;
            // 表引擎
            pattern = Pattern.compile("ENGINE=([0-9a-zA-Z]+)");
            matcher = pattern.matcher(token);
            if (matcher.find()) {
                currentTable.setEngine(TableEngine.valueOf(TABLE_ENGINE_PREFIX + matcher.toMatchResult().group(1)));
            }
            // 字符类型
            pattern = Pattern.compile("CHARSET=([0-9a-zA-Z]+)");
            matcher = pattern.matcher(token);
            if (matcher.find()) {
                currentTable.setCharacterSet(CharacterSet.valueOf(TABLE_ENGINE_PREFIX + matcher.toMatchResult().group(1).toUpperCase()));
            }
            // 表注释
            pattern = Pattern.compile("COMMENT='(.*)");
            matcher = pattern.matcher(token);
            if (matcher.find()) {
                currentTable.setComment(matcher.toMatchResult().group(1));
            }
            currentTable.setDataBaseType(DbType.MySQL);
            return true;
        }
        return false;
    }

    /**
     * 处理表开始
     *
     * @param token
     * @return
     */
    private String parseTableStart(String token) {
        if (StringUtils.startsWith(token.trim(), CREATE_TABLE)) {
            Pattern pattern = Pattern.compile("`([a-zA-Z0-9_]+)`");
            Matcher matcher = pattern.matcher(token);
            if (matcher.find()) {
                return StringUtils.unwrap(matcher.toMatchResult().group(), '`');
            }
        }
        return null;
    }
}
