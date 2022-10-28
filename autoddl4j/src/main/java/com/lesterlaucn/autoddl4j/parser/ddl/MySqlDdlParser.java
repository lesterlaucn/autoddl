package com.lesterlaucn.autoddl4j.parser.ddl;


import com.lesterlaucn.autoddl4j.datasource.definition.CharacterSet;
import com.lesterlaucn.autoddl4j.datasource.definition.TableEngine;
import com.lesterlaucn.autoddl4j.parser.EntityParserResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyuancheng on 2022/10/27  <br/>
 *
 * @author liuyuancheng
 */
@Slf4j
public class MySqlDdlParser {

    private static final String CREATE_TABLE = "CREATE TABLE";

    private static final String TABLE_ENGINE_PREFIX = "MySQL_";

    private String ddl;

    private EntityParserResult result = EntityParserResult.create();

    public MySqlDdlParser(String ddl) {
        this.ddl = ddl;
    }

    //TODO 未完成
    public EntityParserResult parse() {
        final StringTokenizer tokenizer = new StringTokenizer(ddl, "\n");
        EntityParserResult.Table currentTable = null;
        boolean isColumnDefinition = false;
        while (tokenizer.hasMoreElements()) {
            final String token = tokenizer.nextToken();
            if (StringUtils.isBlank(token)) continue;
            // 处理表(开始)
            final String tableName = parseTableStart(token);
            if (!isColumnDefinition && StringUtils.isNotEmpty(tableName)) {
                log.debug("parse table {}", tableName);
                currentTable = this.result.getTable(tableName);
                currentTable.setTableName(tableName);
                isColumnDefinition = true;
                continue;
            }
            // 处理表(结束)
            if (isColumnDefinition && parseTableEnd(token, currentTable)) {
                isColumnDefinition = false;
            }
            // 处理字段
            if (isColumnDefinition) {
                parseColumn(token, currentTable);
            }
        }
        return result;
    }

    /**
     * 处理表字段
     *
     * @param token
     * @param currentTable
     */
    private void parseColumn(String token, EntityParserResult.Table currentTable) {
        final EntityParserResult.Column column = new EntityParserResult.Column();
        Pattern pattern = null;
        Matcher matcher = null;
        pattern = Pattern.compile("`([a-zA-Z0-9_]+)`");
        matcher = pattern.matcher(token);
        if (!matcher.find()) {
            log.warn("colum not found for token: {}", token);
            return;
        }
        column.setColumnName(matcher.toMatchResult().group(1));
        column.setIsNullable(!token.contains("NOT NULL"));
        pattern = Pattern.compile("DEFAULT '([.]+)'");
        matcher = pattern.matcher(token);
        if (matcher.find()){
            column.setDefaultValue(matcher.toMatchResult().group(1));
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
    private boolean parseTableEnd(String token, EntityParserResult.Table currentTable) {
        if (StringUtils.startsWith(token.trim(), ")")) {
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile("ENGINE=([0-9a-zA-Z]+)");
            matcher = pattern.matcher(token);
            if (matcher.find()) {
                currentTable.setEngine(TableEngine.valueOf(TABLE_ENGINE_PREFIX + matcher.toMatchResult().group(1)));
            }
            pattern = Pattern.compile("CHARSET=([0-9a-zA-Z]+)");
            matcher = pattern.matcher(token);
            if (matcher.find()) {
                currentTable.setCharacterSet(CharacterSet.valueOf(TABLE_ENGINE_PREFIX + matcher.toMatchResult().group(1).toUpperCase()));
            }
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
